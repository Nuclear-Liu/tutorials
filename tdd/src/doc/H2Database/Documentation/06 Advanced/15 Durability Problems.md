## Durability Problems *持久化问题*

Complete durability means all committed transaction survive a power failure.
Some databases claim they can guarantee durability, but such claims are wrong.
A durability test was run against H2, HSQLDB, PostgreSQL, and Derby.
All of those databases sometimes lose committed transactions.
The test is included in the H2 download, see `org.h2.test.poweroff.Test`.


完整的持久性意味着所有提交的事务在断电后仍然存在。
一些数据库声称它们可以保证持久性，但这种说法是错误的。
针对 H2、HSQLDB、PostgreSQL 和 Derby 运行了持久性测试。
所有这些数据库有时都会丢失已提交的事务。
该测试包含在 H2 下载中，请参阅 `org.h2.test.poweroff.Test` 。

---

### Ways to (Not) Achieve Durability *实现（不）持久性的方法*

Making sure that committed transactions are not lost is more complicated than it seems first.
To guarantee complete durability, a database must ensure that the log record is on the hard drive before the commit call returns.
To do that, databases use different methods.
One is to use the 'synchronous write' file access mode.
In Java, `RandomAccessFile` supports the modes `rws` and `rwd`:


确保提交的事务不会丢失比看起来更复杂。
为了保证完全的持久性，数据库必须确保在提交调用返回之前日志记录在硬盘上。
为此，数据库使用不同的方法。
一种是使用“同步写入”文件访问模式。
在 Java 中，`RandomAccessFile` 支持模式 `rws` 和 `rwd` ：

---

* `rwd`: every update to the file's content is written synchronously to the underlying storage device.
* `rws`: in addition to rwd, every update to the metadata is written synchronously.


* `rwd` ：文件内容的每次更新都会同步写入底层存储设备。
* `rws` ：除了 rwd 之外，对元数据的每次更新都是同步写入的。

---

A test (`org.h2.test.poweroff.TestWrite`) with one of those modes achieves around 50 thousand write operations per second.
Even when the operating system write buffer is disabled, the write rate is around 50 thousand operations per second.
This feature does not force changes to disk because it does not flush all buffers.
The test updates the same byte in the file again and again.
If the hard drive was able to write at this rate, then the disk would need to make at least 50 thousand revolutions per second, or 3 million RPM (revolutions per minute).
There are no such hard drives.
The hard drive used for the test is about 7200 RPM, or about 120 revolutions per second.
There is an overhead, so the maximum write rate must be lower than that.


使用其中一种模式的测试 ( `org.h2.test.poweroff.TestWrite` ) 每秒可实现约 5 万次写入操作。
即使禁用操作系统写入缓冲区，写入速率也约为每秒 5 万次操作。
此功能不会强制更改磁盘，因为它不会刷新所有缓冲区。
测试一次又一次地更新文件中的相同字节。
如果硬盘驱动器能够以这种速度写入，那么磁盘至少需要每秒旋转 5 万转，即 300 万 RPM（每分钟转数）。
没有这样的硬盘。
用于测试的硬盘大约为 7200 RPM，即大约每秒 120 转。
存在开销，因此最大写入速率必须低于此值。

---

Calling `fsync` flushes the buffers. 
There are two ways to do that in Java:


调用 `fsync` 会刷新缓冲区。
在 Java 中有两种方法可以做到这一点：

---

* `FileDescriptor.sync()`.
  The documentation says that this forces all system buffers to synchronize with the underlying device.
  This method is supposed to return after all in-memory modified copies of buffers associated with this file descriptor have been written to the physical medium.
* `FileChannel.force()`.
  This method is supposed to force any updates to this channel's file to be written to the storage device that contains it.
  

* `FileDescriptor.sync()`.
  文档说这会强制所有系统缓冲区与底层设备同步。
  此方法应该在与此文件描述符关联的缓冲区的所有内存中修改副本已写入物理介质后返回。
* `FileChannel.force()`.
  此方法应该强制将此通道文件的任何更新写入包含它的存储设备。
  
---

By default, MySQL calls `fsync` for each commit.
When using one of those methods, only around 60 write operations per second can be achieved, which is consistent with the RPM rate of the hard drive used.
Unfortunately, even when calling `FileDescriptor.sync()` or `FileChannel.force()`, data is not always persisted to the hard drive, because most hard drives do not obey `fsync()`: 
see [Your Hard Drive Lies to You]().
In Mac OS X, fsync does not flush hard drive buffers. 
See [Bad fsync?]().
So the situation is confusing, and tests prove there is a problem.


默认情况下，MySQL 为每个提交调用 `fsync`。
使用其中一种方法时，每秒只能实现大约 60 次写入操作，这与所用硬盘的 RPM 速率一致。
不幸的是，即使在调用 `FileDescriptor.sync()` 或 `FileChannel.force()` 时，数据也并不总是持久化到硬盘上，因为大多数硬盘驱动器不遵守 `fsync()`：
参见 [Your Hard Drive Lies to You]() 。
在 Mac OS X 中，fsync 不会刷新硬盘缓冲区。
参见 [Bad fsync?]() 。
所以情况很混乱，测试证明有问题。

---

Trying to flush hard drive buffers is hard, and if you do the performance is very bad. 
First you need to make sure that the hard drive actually flushes all buffers.
Tests show that this can not be done in a reliable way.
Then the maximum number of transactions is around 60 per second.
Because of those reasons, the default behavior of H2 is to delay writing committed transactions.


尝试刷新硬盘驱动器缓冲区很困难，如果这样做，性能会很差。
首先，您需要确保硬盘驱动器确实刷新了所有缓冲区。
测试表明，这不能以可靠的方式完成。
那么最大事务数约为每秒 60 个。
由于这些原因， H2 的默认行为是延迟写入已提交的事务。

---

In H2, after a power failure, a bit more than one second of committed transactions may be lost.
To change the behavior, use `SET WRITE_DELAY` and `CHECKPOINT SYNC`.
Most other databases support commit delay as well.
In the performance comparison, commit delay was used for all databases that support it.


在 H2 中，断电后，可能会丢失多于一秒的已提交事务。
要更改行为，请使用 `SET WRITE_DELAY` 和 `CHECKPOINT SYNC` 。
大多数其他数据库也支持提交延迟。
在性能比较中，所有支持它的数据库都使用了提交延迟。

---

### Running the Durability Test *运行持久性测试*

To test the durability / non-durability of this and other databases, you can use the test application in the package `org.h2.test.poweroff`.
Two computers with network connection are required to run this test.
One computer just listens, while the test application is run (and power is cut) on the other computer.
The computer with the listener application opens a TCP/IP port and listens for an incoming connection.
The second computer first connects to the listener, and then created the databases and starts inserting records.
The connection is set to 'autocommit', which means after each inserted record a commit is performed automatically.
Afterwards, the test computer notifies the listener that this record was inserted successfully.
The listener computer displays the last inserted record number every 10 seconds.
Now, switch off the power manually, then restart the computer, and run the application again.
You will find out that in most cases, none of the databases contains all the records that the listener computer knows about.
For details, please consult the source code of the listener and test application.


要测试此数据库和其他数据库的持久性/非持久性，您可以使用包 `org.h2.test.poweroff` 中的测试应用程序。
运行此测试需要两台具有网络连接的计算机。
一台计算机只是监听，而测试应用程序在另一台计算机上运行（并切断电源）。
带有侦听器应用程序的计算机打开一个 TCP/IP 端口并侦听传入连接。
第二台计算机首先连接到侦听器，然后创建数据库并开始插入记录。
连接设置为“自动提交”，这意味着在每个插入的记录后自动执行提交。
之后，测试计算机通知监听者这条记录插入成功。
侦听器计算机每 10 秒显示一次最后插入的记录号。
现在，手动关闭电源，然后重新启动计算机，并再次运行该应用程序。
您会发现在大多数情况下，没有一个数据库包含侦听器计算机知道的所有记录。
详情请查阅监听器和测试应用程序的源代码。

---
