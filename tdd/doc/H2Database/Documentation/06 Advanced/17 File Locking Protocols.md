## File Locking Protocols *文件锁定协议*

Multiple concurrent connections to the same database are supported, however a database file can only be open for reading and writing (in embedded mode) by one process at the same time.
Otherwise, the processes would overwrite each others data and corrupt the database file.
To protect against this problem, whenever a database is opened, a lock file is created to signal other processes that the database is in use.
If the database is closed, or if the process that opened the database stops normally, this lock file is deleted.


支持到同一个数据库的多个并发连接，但是一个数据库文件只能同时被一个进程打开进行读写（嵌入模式）。
否则，进程将覆盖彼此的数据并损坏数据库文件。
为防止出现此问题，每当打开数据库时，都会创建一个锁定文件，以向其他进程发出该数据库正在使用中的信号。
如果数据库关闭，或者打开数据库的进程正常停止，这个锁文件就会被删除。

---

In special cases (if the process did not terminate normally, for example because there was a power failure), the lock file is not deleted by the process that created it.
That means the existence of the lock file is not a safe protocol for file locking.
However, this software uses a challenge-response protocol to protect the database files.
There are two methods (algorithms) implemented to provide both security (that is, the same database files cannot be opened by two processes at the same time) and simplicity (that is, the lock file does not need to be deleted manually by the user).
The two methods are 'file method' and 'socket methods'.


在特殊情况下（如果进程没有正常终止，例如因为电源故障），创建它的进程不会删除锁定文件。
这意味着锁定文件的存在不是文件锁定的安全协议。
但是，该软件使用质询-响应协议来保护数据库文件。
实现了两种方法（算法）来提供安全性（即两个进程不能同时打开同一个数据库文件）和简单性（即不需要用户手动删除锁定文件） ）。
这两种方法是“文件方法”和“套接字方法”。

---

The file locking protocols (except the file locking method 'FS') have the following limitation: 
if a shared file system is used, and the machine with the lock owner is sent to sleep (standby or hibernate), another machine may take over.
If the machine that originally held the lock wakes up, the database may become corrupt.
If this situation can occur, the application must ensure the database is closed when the application is put to sleep.


文件锁定协议（文件锁定方法“FS”除外）具有以下限制：
如果使用共享文件系统，并且具有锁所有者的机器被发送到睡眠状态（待机或休眠），另一台机器可能会接管。
如果最初持有锁的机器被唤醒，数据库可能会损坏。
如果发生这种情况，应用程序必须确保在应用程序进入睡眠状态时关闭数据库。

---

### File Locking Method 'File' *文件锁定方法 'File'*

The default method for database file locking for version 1.3 and older is the 'File Method'.
The algorithm is:


1.3 及更早版本的数据库文件锁定的默认方法是 'File Method' 。
算法是：

---

* If the lock file does not exist, it is created (using the atomic operation `File.createNewFile`).
  Then, the process waits a little bit (20 ms) and checks the file again.
  If the file was changed during this time, the operation is aborted.
  This protects against a race condition when one process deletes the lock file just after another one create it, and a third process creates the file again.
  It does not occur if there are only two writers.
* If the file can be created, a random number is inserted together with the locking method ('file').
  Afterwards, a watchdog thread is started that checks regularly (every second once by default) if the file was deleted or modified by another (challenger) thread / process.
  Whenever that occurs, the file is overwritten with the old data.
  The watchdog thread runs with high priority so that a change to the lock file does not get through undetected even if the system is very busy.
  However, the watchdog thread does use very little resources (CPU time), because it waits most of the time.
  Also, the watchdog only reads from the hard disk and does not write to it.
* If the lock file exists and was recently modified, the process waits for some time (up to two seconds).
  If it was still changed, an exception is thrown (database is locked).
  This is done to eliminate race conditions with many concurrent writers.
  Afterwards, the file is overwritten with a new version (challenge).
  After that, the thread waits for 2 seconds.
  If there is a watchdog thread protecting the file, he will overwrite the change and this process will fail to lock the database.
  However, if there is no watchdog thread, the lock file will still be as written by this thread.
  In this case, the file is deleted and atomically created again.
  The watchdog thread is started in this case and the file is locked.


* 如果锁文件不存在，则创建它（使用原子操作 `File.createNewFile` ）。
  然后，进程稍等（20 毫秒）并再次检查文件。
  如果在此期间更改了文件，则操作将中止。
  当一个进程在另一个进程创建锁定文件后立即删除它，并且第三个进程再次创建该文件时，这可以防止竞争条件。
  如果只有两个作者，则不会发生这种情况。
* 如果可以创建文件，则将随机数与锁定方法（'file'）一起插入。
  之后，会启动一个看门狗线程，它会定期（默认情况下每秒钟检查一次）检查文件是否被另一个（挑战者）线程进程删除或修改。
  每当发生这种情况时，文件就会被旧数据覆盖。
  看门狗线程以高优先级运行，因此即使系统非常繁忙，对锁定文件的更改也不会未被检测到。
  但是，看门狗线程确实使用很少的资源（CPU 时间），因为它大部分时间都在等待。
  此外，看门狗只从硬盘读取，不写入。
* 如果锁定文件存在并且最近被修改过，则进程会等待一段时间（最多两秒）。
  如果它仍然被更改，则抛出异常（数据库被锁定）。
  这样做是为了消除许多并发编写器的竞争条件。
  之后，该文件将被新版本（挑战）覆盖。
  之后，线程等待 2 秒。
  如果存在保护文件的看门狗线程，他将覆盖更改并且此过程将无法锁定数据库。
  但是，如果没有看门狗线程，锁文件仍然是这个线程写的。
  在这种情况下，文件被删除并再次自动创建。
  在这种情况下启动看门狗线程并锁定文件。
  
---

This algorithm is tested with over 100 concurrent threads.
In some cases, when there are many concurrent threads trying to lock the database, they block each other (meaning the file cannot be locked by any of them) for some time.
However, the file never gets locked by two threads at the same time. 
However using that many concurrent threads / processes is not the common use case. 
Generally, an application should throw an error to the user if it cannot open a database, and not try again in a (fast) loop.


该算法经过 100 多个并发线程的测试。
在某些情况下，当有许多并发线程试图锁定数据库时，它们会在一段时间内相互阻塞（意味着文件不能被任何一个锁定）。
但是，文件永远不会同时被两个线程锁定。
然而，使用这么多并发线程进程并不是常见的用例。
通常，如果应用程序无法打开数据库，应用程序应该向用户抛出错误，并且不会在（快速）循环中重试。

---

### File Locking Method 'Socket' *文件锁定方法 'Socket'*

There is a second locking mechanism implemented, but disabled by default.
To use it, append `;FILE_LOCK=SOCKET` to the database URL.
The algorithm is:


实现了第二个锁定机制，但默认情况下禁用。
要使用它，请将 `;FILE_LOCK=SOCKET` 附加到数据库 URL。
算法是：

---

* If the lock file does not exist, it is created.
  Then a server socket is opened on a defined port, and kept open.
  The port and IP address of the process that opened the database is written into the lock file.
* If the lock file exists, and the lock method is 'file', then the software switches to the 'file' method.
* If the lock file exists, and the lock method is 'socket', then the process checks if the port is in use.
  If the original process is still running, the port is in use and this process throws an exception (database is in use).
  If the original process died (for example due to a power failure, or abnormal termination of the virtual machine), then the port was released.
  The new process deletes the lock file and starts again.
  

* 如果锁定文件不存在，则创建它。
  然后在定义的端口上打开一个服务器套接字，并保持打开状态。
  打开数据库的进程的端口和 IP 地址写入锁定文件。
* 如果锁定文件存在，并且锁定方法为“文件”，则软件切换到“文件”方法。
* 如果锁文件存在，并且锁方法是'socket'，那么进程检查端口是否在使用中。
  如果原始进程仍在运行，则端口正在使用中，并且此进程会抛出异常（数据库正在使用中）。
  如果原始进程终止（例如由于电源故障或虚拟机异常终止），则端口被释放。
  新进程删除锁定文件并重新启动。
  
---

This method does not require a watchdog thread actively polling (reading) the same file every second.
The problem with this method is, if the file is stored on a network share, two processes (running on different computers) could still open the same database files, if they do not have a direct TCP/IP connection.


这种方法不需要看门狗线程每秒主动轮询（读取）同一个文件。
这种方法的问题是，如果文件存储在网络共享上，两个进程（运行在不同的计算机上）仍然可以打开相同的数据库文件，如果它们没有直接的 TCPIP 连接。

---

### File Locking Method 'FS' *文件锁定方法 'FS'*

This is the default mode for version 1.4 and newer.
This database file locking mechanism uses native file system lock on the database file.
No *.lock.db file is created in this case, and no background thread is started.
This mechanism may not work on all systems as expected.
Some systems allow to lock the same file multiple times within the same virtual machine, and on some system native file locking is not supported or files are not unlocked after a power failure.


这是 1.4 及更新版本的默认模式。
此数据库文件锁定机制对数据库文件使用本机文件系统锁定。
在这种情况下不会创建 *.lock.db 文件，也不会启动后台线程。
此机制可能无法按预期在所有系统上运行。
有些系统允许在同一个虚拟机内多次锁定同一个文件，而在某些系统上，本机文件锁定不受支持或断电后文件未解锁。

---

To enable this feature, append `;FILE_LOCK=FS` to the database URL.


要启用此功能，请将 `;FILE_LOCK=FS` 附加到数据库 URL。

---

This feature is relatively new.
When using it for production, please ensure your system does in fact lock files as expected.


这个功能比较新。
将其用于生产时，请确保您的系统确实按预期锁定文件。

---
