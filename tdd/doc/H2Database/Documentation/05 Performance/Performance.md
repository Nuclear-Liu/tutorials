# Performance *性能*

## Performance Comparison *性能比较*

In many cases H2 is faster than other (open source and not open source) database engines. 
Please note this is mostly a single connection benchmark run on one computer, with many very simple operations running against the database. 
This benchmark does not include very complex queries. 
The embedded mode of H2 is faster than the client-server mode because the per-statement overhead is greatly reduced.


在许多情况下，H2 比其他（开源和非开源）数据库引擎更快。
请注意，这主要是在一台计算机上运行的单一连接基准测试，其中有许多针对数据库运行的非常简单的操作。
此基准测试不包括非常复杂的查询。
H2 的嵌入模式比客户端-服务器模式更快，因为每个语句的开销大大减少。

---

### Embedded *嵌入式*

| Test Case | Unit | H2 | HSQLDB | Derby |
| ---- | ---- | ---- | ---- | ---- |
| Simple: Init | ms | 1019 | 1907 | 8280 |
| Simple: Query (random) | ms | 1304 | 873 | 1912 |
| Simple: Query (sequential) | ms | 835 | 1839 | 5415 |
| Simple: Update (sequential) | ms | 961 | 2333 | 21759 |
| Simple: Delete (sequential) | ms | 950 | 1922 | 32016 |
| Simple: Memory Usage | MB | 21 | 10 | 8 |
| BenchA: Init | ms | 919 | 2133 | 7528 |
| BenchA: Transactions | ms | 1219 | 2297 | 8541 |
| BenchA: Memory Usage | MB | 12 | 15 | 7 |
| BenchB: Init | ms | 905 | 1993 | 8049 |
| BenchB: Transactions | ms | 1091 | 583 | 1165 |
| BenchB: Memory Usage | MB | 17 | 11 | 8 |
| BenchC: Init | ms | 2491 | 4003 | 8064 |
| BenchC: Transactions | ms | 1979 | 803 | 2840 |
| BenchC: Memory Usage | MB | 19 | 22 | 9 |
| Executed statements | # | 1930995 | 1930995 | 1930995 |
| Total time | ms | 13673 | 20686 | 105569 |
| Statements per second | # | 141226 | 93347 | 18291 |


| 测试案例 | 单位 | H2 | HSQLDB | Derby |
| ---- | ---- | ---- | ---- | ---- |
| Simple: Init | ms | 1019 | 1907 | 8280 |
| Simple: Query (random) | ms | 1304 | 873 | 1912 |
| Simple: Query (sequential) | ms | 835 | 1839 | 5415 |
| Simple: Update (sequential) | ms | 961 | 2333 | 21759 |
| Simple: Delete (sequential) | ms | 950 | 1922 | 32016 |
| Simple: Memory Usage | MB | 21 | 10 | 8 |
| BenchA: Init | ms | 919 | 2133 | 7528 |
| BenchA: Transactions | ms | 1219 | 2297 | 8541 |
| BenchA: Memory Usage | MB | 12 | 15 | 7 |
| BenchB: Init | ms | 905 | 1993 | 8049 |
| BenchB: Transactions | ms | 1091 | 583 | 1165 |
| BenchB: Memory Usage | MB | 17 | 11 | 8 |
| BenchC: Init | ms | 2491 | 4003 | 8064 |
| BenchC: Transactions | ms | 1979 | 803 | 2840 |
| BenchC: Memory Usage | MB | 19 | 22 | 9 |
| Executed statements | # | 1930995 | 1930995 | 1930995 |
| Total time | ms | 13673 | 20686 | 105569 |
| Statements per second | # | 141226 | 93347 | 18291 |

---

### Client-Server *客户端-服务器*

| Test Case | Unit | H2 (Server) | HSQLDB | Derby | PostgreSQL | MySQL |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| Simple: Init | ms | 16338 | 17198 | 27860 | 30156 | 29409 |
| Simple: Query (random) | ms | 3399 | 2582 | 6190 | 3315 | 3342 |
| Simple: Query (sequential) | ms | 21841 | 18699 | 42347 | 30774 | 32611 |
| Simple: Update (sequential) | ms | 6913 | 7745 | 28576 | 32698 | 11350 |
| Simple: Delete (sequential) | ms | 8051 | 9751 | 42202 | 44480 | 16555 |
| Simple: Memory Usage | MB | 22 | 11 | 9 | 0 | 1 |
| BenchA: Init | ms | 12996 | 14720 | 24722 | 26375 | 26060 |
| BenchA: Transactions | ms | 10134 | 10250 | 18452 | 21453 | 15877 |
| BenchA: Memory Usage | MB | 13 | 15 | 9 | 0 | 1 |
| BenchB: Init | ms | 15264 | 16889 | 28546 | 31610 | 29747 |
| BenchB: Transactions | ms | 3017 | 3376 | 1842 | 2771 | 1433 |
| BenchB: Memory Usage | MB | 17 | 12 | 11 | 1 | 1 |
| BenchC: Init | ms | 14020 | 10407 | 17655 | 19520 | 17532 |
| BenchC: Transactions | ms | 5076 | 3160 | 6411 | 6063 | 4530 |
| BenchC: Memory Usage | MB | 19 | 21 | 11 | 1 | 1 |
| Executed statements | # | 1930995 | 1930995 | 1930995 | 1930995 | 1930995 |
| Total time | ms | 117049 | 114777 | 244803 | 249215 | 188446 |
| Statements per second | # | 16497 | 16823 | 7887 | 7748 | 10246 |


| 测试案例 | 单位 | H2 (Server) | HSQLDB | Derby | PostgreSQL | MySQL |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| Simple: Init | ms | 16338 | 17198 | 27860 | 30156 | 29409 |
| Simple: Query (random) | ms | 3399 | 2582 | 6190 | 3315 | 3342 |
| Simple: Query (sequential) | ms | 21841 | 18699 | 42347 | 30774 | 32611 |
| Simple: Update (sequential) | ms | 6913 | 7745 | 28576 | 32698 | 11350 |
| Simple: Delete (sequential) | ms | 8051 | 9751 | 42202 | 44480 | 16555 |
| Simple: Memory Usage | MB | 22 | 11 | 9 | 0 | 1 |
| BenchA: Init | ms | 12996 | 14720 | 24722 | 26375 | 26060 |
| BenchA: Transactions | ms | 10134 | 10250 | 18452 | 21453 | 15877 |
| BenchA: Memory Usage | MB | 13 | 15 | 9 | 0 | 1 |
| BenchB: Init | ms | 15264 | 16889 | 28546 | 31610 | 29747 |
| BenchB: Transactions | ms | 3017 | 3376 | 1842 | 2771 | 1433 |
| BenchB: Memory Usage | MB | 17 | 12 | 11 | 1 | 1 |
| BenchC: Init | ms | 14020 | 10407 | 17655 | 19520 | 17532 |
| BenchC: Transactions | ms | 5076 | 3160 | 6411 | 6063 | 4530 |
| BenchC: Memory Usage | MB | 19 | 21 | 11 | 1 | 1 |
| Executed statements | # | 1930995 | 1930995 | 1930995 | 1930995 | 1930995 |
| Total time | ms | 117049 | 114777 | 244803 | 249215 | 188446 |
| Statements per second | # | 16497 | 16823 | 7887 | 7748 | 10246 |

---

### Benchmark Results and Comments *基准测试结果和评论*

#### H2 *H2*

Version 1.4.177 (2014-04-12) was used for the test. 
For most operations, the performance of H2 is about the same as for HSQLDB. 
One situation where H2 is slow is large result sets, because they are buffered to disk if more than a certain number of records are returned. 
The advantage of buffering is: there is no limit on the result set size.


版本 1.4.177 (2014-04-12) 用于测试。
对于大多数操作，H2 的性能与 HSQLDB 大致相同。
H2 较慢的一种情况是大型结果集，因为如果返回的记录数超过一定数量，它们将被缓冲到磁盘。
缓冲的优点是：结果集大小没有限制。

---

#### HSQLDB *HSQLDB*

Version 2.3.2 was used for the test. 
Cached tables are used in this test (`hsqldb.default_table_type=cached`), and the write delay is 1 second (`SET WRITE_DELAY 1`).


版本 2.3.2 用于测试。
本次测试使用缓存表（ `hsqldb.default_table_type=cached` ），写入延迟为1秒（ `SET WRITE_DELAY 1` ）。

---

#### Derby *Derby*

Version 10.10.1.1 was used for the test. 
Derby is clearly the slowest embedded database in this test. 
This seems to be a structural problem, because all operations are really slow. 
It will be hard for the developers of Derby to improve the performance to a reasonable level. 
A few problems have been identified: leaving autocommit on is a problem for Derby. 
If it is switched off during the whole test, the results are about 20% better for Derby. 
Derby calls `FileChannel.force(false)`, but only twice per log file (not on each commit). 
Disabling this call improves performance for Derby by about 2%. 
Unlike H2, Derby does not call `FileDescriptor.sync()` on each checkpoint. 
Derby supports a testing mode (system property `derby.system.durability=test`) where durability is disabled. 
According to the documentation, this setting should be used for testing only, as the database may not recover after a crash. 
Enabling this setting improves performance by a factor of 2.6 (embedded mode) or 1.4 (server mode). 
Even if enabled, Derby is still less than half as fast as H2 in default mode.


版本 10.10.1.1 用于测试。
Derby 显然是本次测试中最慢的嵌入式数据库。
这似乎是一个结构性问题，因为所有操作都非常缓慢。
Derby 的开发人员很难将性能提高到合理的水平。
已经确定了一些问题：保持自动提交是 Derby 的一个问题。
如果在整个测试过程中关闭它，德比的结果会好 20% 左右。
Derby 调用 `FileChannel.force(false)` ，但每个日志文件只调用两次（不是每次提交时）。
禁用此调用可将 Derby 的性能提高约 2%。
与 H2 不同，Derby 不会在每个检查点上调用 `FileDescriptor.sync()` 。
Derby 支持禁用耐久性的测试模式（系统属性 `derby.system.durability=test` ）。
根据文档，此设置应仅用于测试，因为数据库在崩溃后可能无法恢复。
启用此设置可将性能提高 2.6 倍（嵌入式模式）或 1.4 倍（服务器模式）。
即使启用，在默认模式下，Derby 的速度仍然不及 H2 的一半。

---

#### PostgreSQL *PostgreSQL*

Version 9.1.5 was used for the test. 
The following options where changed in `postgresql.conf: fsync = off, commit_delay = 1000`. 
PostgreSQL is run in server mode. 
The memory usage number is incorrect, because only the memory usage of the JDBC driver is measured.


版本 9.1.5 用于测试。
`postgresql.conf 中更改了以下选项：fsync = off, commit_delay = 1000`。
PostgreSQL 在服务器模式下运行。
内存使用量不正确，因为只测量了JDBC驱动程序的内存使用量。

---

#### MySQL *MySQL*

Version 5.1.65-log was used for the test. 
MySQL was run with the InnoDB backend. 
The setting `innodb_flush_log_at_trx_commit` (found in the `my.ini` / `my.cnf` file) was set to 0. Otherwise (and by default), MySQL is slow (around 140 statements per second in this test) because it tries to flush the data to disk for each commit. 
For small transactions (when autocommit is on) this is really slow. 
But many use cases use small or relatively small transactions. 
Too bad this setting is not listed in the configuration wizard, and it always overwritten when using the wizard. 
You need to change this setting manually in the file `my.ini` / `my.cnf`, and then restart the service. 
The memory usage number is incorrect, because only the memory usage of the JDBC driver is measured.


版本 5.1.65-log 用于测试。
MySQL 与 InnoDB 后端一起运行。
设置 `innodb_flush_log_at_trx_commit`（在 `my.ini` `my.cnf` 文件中找到）被设置为 0。否则（默认情况下），MySQL 很慢（在这个测试中每秒大约 140 个语句），因为它试图每次提交将数据刷新到磁盘。
对于小事务（当自动提交打开时）这真的很慢。
但许多用例使用较小或相对较小的事务。.
可惜此设置未在配置向导中列出，并且在使用向导时总是会被覆盖。
您需要在文件 `my.ini` / `my.cnf` 中手动更改此设置，然后重新启动服务。
内存使用量不正确，因为只测量了JDBC驱动程序的内存使用量。

---

#### Firebird *Firebird*

Firebird 1.5 (default installation) was tested, but the results are not published currently. 
It is possible to run the performance test with the Firebird database, and any information on how to configure Firebird for higher performance are welcome.


Firebird 1.5（默认安装）已经过测试，但目前未发布结果
可以使用 Firebird 数据库运行性能测试，欢迎提供有关如何配置 Firebird 以获得更高性能的任何信息。

---

#### Why Oracle / MS SQL Server / DB2 are Not Listed *为什么未列出 Oracle MS SQL Server DB2*

The license of these databases does not allow to publish benchmark results. 
This doesn't mean that they are fast. 
They are in fact quite slow, and need a lot of memory. 
But you will need to test this yourself. 
SQLite was not tested because the JDBC driver doesn't support transactions.


这些数据库的许可不允许发布基准测试结果。
这并不意味着它们很快。
它们实际上很慢，并且需要大量内存。
但是你需要自己测试一下。
SQLite 未经过测试，因为 JDBC 驱动程序不支持事务。

---

### About this Benchmark *关于这个基准*

#### How to Run *如何跑步*

This test was as follows:


该测试如下：

---

`build benchmark`

#### Separate Process per Database *每个数据库的单独进程*

For each database, a new process is started, to ensure the previous test does not impact the current test.


对于每个数据库，都会启动一个新进程，以确保之前的测试不会影响当前的测试。

---

#### Number of Connections *连接数*

This is mostly a single-connection benchmark. 
BenchB uses multiple connections; the other tests use one connection.


这主要是单连接基准测试。
BenchB 使用多个连接；其他测试使用一个连接。

---

#### Real-World Tests *真实世界的测试*

Good benchmarks emulate real-world use cases. 
This benchmark includes 4 test cases: BenchSimple uses one table and many small updates / deletes. 
BenchA is similar to the TPC-A test, but single connection / single threaded (see also: www.tpc.org). 
BenchB is similar to the TPC-B test, using multiple connections (one thread per connection). 
BenchC is similar to the TPC-C test, but single connection / single threaded.


良好的基准模拟真实世界的用例。
该基准测试包括 4 个测试用例：BenchSimple 使用一张表和许多小的更新/删除。
BenchA 类似于 TPC-A 测试，但单连接单线程（另见：www.tpc.org）。
BenchB 类似于 TPC-B 测试，使用多个连接（每个连接一个线程）。
BenchC 类似于 TPC-C 测试，但单连接单线程。

---

#### Comparing Embedded with Server Databases *比较嵌入式与服务器数据库*

This is mainly a benchmark for embedded databases (where the application runs in the same virtual machine as the database engine). 
However MySQL and PostgreSQL are not Java databases and cannot be embedded into a Java application. 
For the Java databases, both embedded and server modes are tested. 


这主要是嵌入式数据库（应用程序与数据库引擎在同一虚拟机中运行）的基准测试。
但是 MySQL 和 PostgreSQL 不是 Java 数据库，不能嵌入到 Java 应用程序中。
对于 Java 数据库，测试了嵌入式和服务器模式。

---

#### Test Platform *测试平台*

This test is run on Mac OS X 10.6. No virus scanner was used, and disk indexing was disabled. 
The JVM used is Sun JDK 1.6.


此测试在 Mac OS X 10.6 上运行。没有使用病毒扫描程序，并且禁用了磁盘索引。
使用的 JVM 是 Sun JDK 1.6。

---

#### Multiple Runs *多次运行*

When a Java benchmark is run first, the code is not fully compiled and therefore runs slower than when running multiple times. 
A benchmark should always run the same test multiple times and ignore the first run(s). 
This benchmark runs three times, but only the last run is measured.


当首先运行 Java 基准测试时，代码未完全编译，因此运行速度比多次运行时慢。
基准测试应始终多次运行相同的测试并忽略第一次运行。
此基准测试运行 3 次，但仅测量最后一次运行。

---

#### Memory Usage *内存使用情况*

It is not enough to measure the time taken, the memory usage is important as well. 
Performance can be improved by using a bigger cache, but the amount of memory is limited. 
HSQLDB tables are kept fully in memory by default; this benchmark uses 'disk based' tables for all databases. 
Unfortunately, it is not so easy to calculate the memory usage of PostgreSQL and MySQL, because they run in a different process than the test. 
This benchmark currently does not print memory usage of those databases.


仅测量花费的时间是不够的，内存使用情况也很重要。
使用更大的缓存可以提高性能，但内存量是有限的。
默认情况下，HSQLDB 表完全保存在内存中；该基准测试对所有数据库使用“基于磁盘”的表。
不幸的是，计算 PostgreSQL 和 MySQL 的内存使用并不那么容易，因为它们运行在与测试不同的进程中。
此基准测试当前不打印这些数据库的内存使用情况。

---

#### Delayed Operations *延迟操作*

Some databases delay some operations (for example flushing the buffers) until after the benchmark is run. 
This benchmark waits between each database tested, and each database runs in a different process (sequentially).


某些数据库将某些操作（例如刷新缓冲区）延迟到运行基准测试之后。
该基准测试在每个测试的数据库之间等待，并且每个数据库在不同的进程中运行（按顺序）。

---

#### Transaction Commit / Durability *事务提交 / 持久性*

Durability means transaction committed to the database will not be lost. 
Some databases (for example MySQL) try to enforce this by default by calling `fsync()` to flush the buffers, but most hard drives don't actually flush all data. 
Calling the method slows down transaction commit a lot, but doesn't always make data durable. 
When comparing the results, it is important to think about the effect. 
Many database suggest to 'batch' operations when possible. 
This benchmark switches off autocommit when loading the data, and calls commit after each 1000 inserts. 
However many applications need 'short' transactions at runtime (a commit after each update). 
This benchmark commits after each update / delete in the simple benchmark, and after each business transaction in the other benchmarks. 
For databases that support delayed commits, a delay of one second is used.


持久性意味着提交到数据库的事务不会丢失。
一些数据库（例如 MySQL）试图通过调用 `fsync()` 来刷新缓冲区，默认情况下强制执行此操作，但大多数硬盘驱动器实际上并不会刷新所有数据。
调用该方法会大大减慢事务提交的速度，但并不总是使数据持久。
在比较结果时，重要的是要考虑效果。
许多数据库建议在可能的情况下进行“批处理”操作。
此基准测试在加载数据时关闭自动提交，并在每 1000 次插入后调用 commit。
然而，许多应用程序在运行时需要“短”事务（每次更新后提交）。
该基准在简单基准中的每次更新删除后以及其他基准中的每个业务事务后提交。
对于支持延迟提交的数据库，使用一秒的延迟。

---

#### Using Prepared Statements *使用准备好的语句*

Wherever possible, the test cases use prepared statements.


在可能的情况下，测试用例使用准备好的语句。

---

#### Currently Not Tested: Startup Time *当前未测试：启动时间*

The startup time of a database engine is important as well for embedded use. 
This time is not measured currently. 
Also, not tested is the time used to create a database and open an existing database. 
Here, one (wrapper) connection is opened at the start, and for each step a new connection is opened and then closed.


数据库引擎的启动时间对于嵌入式使用也很重要。
目前不测量这个时间。
此外，未测试的是用于创建数据库和打开现有数据库的时间。
在这里，一开始就打开一个（包装器）连接，每一步都会打开一个新的连接，然后关闭。

---

## PolePosition Benchmark *极位置基准*

The PolePosition is an open source benchmark. 
The algorithms are all quite simple. 
It was developed / sponsored by db4o. 
This test was not run for a longer time, so please be aware that the results below are for older database versions (H2 version 1.1, HSQLDB 1.8, Java 1.4).


PolePosition 是一个开源基准测试。
算法都很简单。
它是由 db4o 赞助开发的。
此测试运行时间较长，因此请注意，以下结果适用于较旧的数据库版本（H2 版本 1.1、HSQLDB 1.8、Java 1.4）。

---

| Test Case | Unit | H2 | HSQLDB | MySQL |
| ---- | ---- | ---- | ---- | ---- |
| Melbourne write | ms | 369 | 249 | 2022 |
| Melbourne read | ms | 47 | 49 | 93 |
| Melbourne read_hot | ms | 24 | 43 | 95 |
| Melbourne delete | ms | 147 | 133 | 176 |
| Sepang write | ms | 965 | 1201 | 3213 |
| Sepang read | ms | 765 | 948 | 3455 |
| Sepang read_hot | ms | 789 | 859 | 3563 |
| Sepang delete | ms | 1384 | 1596 | 6214 |
| Bahrain write | ms | 1186 | 1387 | 6904 |
| Bahrain query_indexed_string | ms | 336 | 170 | 693 |
| Bahrain query_string | ms | 18064 | 39703 | 41243 |
| Bahrain query_indexed_int | ms | 104 | 134 | 678 |
| Bahrain update | ms | 191 | 87 | 159 |
| Bahrain delete | ms | 1215 | 729 | 6812 |
| Imola retrieve | ms | 198 | 194 | 4036 |
| Barcelona write | ms | 413 | 832 | 3191 |
| Barcelona read | ms | 119 | 160 | 1177 |
| Barcelona query | ms | 20 | 5169 | 101 |
| Barcelona delete | ms | 388 | 319 | 3287 |
| Total | ms | 26724 | 53962 | 87112 |


| 测试案例 | 单位 | H2 | HSQLDB | MySQL |
| ---- | ---- | ---- | ---- | ---- |
| Melbourne write | ms | 369 | 249 | 2022 |
| Melbourne read | ms | 47 | 49 | 93 |
| Melbourne read_hot | ms | 24 | 43 | 95 |
| Melbourne delete | ms | 147 | 133 | 176 |
| Sepang write | ms | 965 | 1201 | 3213 |
| Sepang read | ms | 765 | 948 | 3455 |
| Sepang read_hot | ms | 789 | 859 | 3563 |
| Sepang delete | ms | 1384 | 1596 | 6214 |
| Bahrain write | ms | 1186 | 1387 | 6904 |
| Bahrain query_indexed_string | ms | 336 | 170 | 693 |
| Bahrain query_string | ms | 18064 | 39703 | 41243 |
| Bahrain query_indexed_int | ms | 104 | 134 | 678 |
| Bahrain update | ms | 191 | 87 | 159 |
| Bahrain delete | ms | 1215 | 729 | 6812 |
| Imola retrieve | ms | 198 | 194 | 4036 |
| Barcelona write | ms | 413 | 832 | 3191 |
| Barcelona read | ms | 119 | 160 | 1177 |
| Barcelona query | ms | 20 | 5169 | 101 |
| Barcelona delete | ms | 388 | 319 | 3287 |
| Total | ms | 26724 | 53962 | 87112 |

---

There are a few problems with the PolePosition test:


olePosition 测试有几个问题：

---

* HSQLDB uses in-memory tables by default while H2 uses persistent tables. 
  The HSQLDB version included in PolePosition does not support changing this, so you need to replace `poleposition-0.20/lib/hsqldb.jar` with a newer version (for example `hsqldb-1.8.0.7.jar`), and then use the setting `hsqldb.connecturl=jdbc:hsqldb:file:data/hsqldb/dbbench2;hsqldb.default_table_type=cached;sql.enforce_size=true` in the file `Jdbc.properties`.
* HSQLDB keeps the database open between tests, while H2 closes the database (losing all the cache). 
  To change that, use the database URL `jdbc:h2:file:data/h2/dbbench;DB_CLOSE_DELAY=-1`
* The amount of cache memory is quite important, specially for the PolePosition test. 
  Unfortunately, the PolePosition test does not take this into account. 
  

* HSQLDB 默认使用内存表，而 H2 使用持久表。
  PolePosition 中包含的 HSQLDB 版本不支持更改此设置，因此您需要将 `poleposition-0.20libhsqldb.jar` 替换为较新的版本（例如 `hsqldb-1.8.0.7.jar`），然后使用设置 `hsqldb .connecturl=jdbc:hsqldb:file:datahsqldbdbbench2;hsqldb.default_table_type=cached;sql.enforce_size=true`在文件`Jdbc.properties`中。
* HSQLDB 在测试之间保持数据库打开，而 H2 关闭数据库（丢失所有缓存）。
  要更改它，请使用数据库 URL `jdbc:h2:file:datah2dbbench;DB_CLOSE_DELAY=-1`
* 高速缓存的数量非常重要，特别是对于 PolePosition 测试。
  不幸的是，PolePosition 测试没有考虑到这一点。
  
---

## Database Performance Tuning *数据库性能调优*

### Keep Connections Open or Use a Connection Pool *保持连接打开或使用连接池*

If your application opens and closes connections a lot (for example, for each request), you should consider using a [connection pool](). 
Opening a connection using `DriverManager.getConnection` is specially slow if the database is closed. 
By default the database is closed if the last connection is closed.


如果您的应用程序打开和关闭连接很多（例如，对于每个请求），您应该考虑使用 [connection pool]() 。
如果数据库关闭，使用`DriverManager.getConnection` 打开连接会特别慢。
默认情况下，如果最后一个连接关闭，则数据库将关闭。

---

If you open and close connections a lot but don't want to use a connection pool, consider keeping a 'sentinel' connection open for as long as the application runs, or use delayed database closing. 
See also [Closing a database]().


如果您经常打开和关闭连接但不想使用连接池，请考虑在应用程序运行期间保持“哨兵”连接打开，或使用延迟关闭数据库。
另见 [Closing a database]() 。

---

### Use a Modern JVM *使用现代 JVM*

Newer JVMs are faster. 
Upgrading to the latest version of your JVM can provide a "free" boost to performance. 
Switching from the default Client JVM to the Server JVM using the `-server` command-line option improves performance at the cost of a slight increase in start-up time.


较新的 JVM 速度更快。
升级到最新版本的 JVM 可以“免费”提升性能。
使用 `-server` 命令行选项从默认的客户端 JVM 切换到服务器 JVM 可以提高性能，但会稍微增加启动时间。

---

### Virus Scanners *病毒扫描程序*

Some virus scanners scan files every time they are accessed. 
It is very important for performance that database files are not scanned for viruses. 
The database engine never interprets the data stored in the files as programs, that means even if somebody would store a virus in a database file, this would be harmless (when the virus does not run, it cannot spread). 
Some virus scanners allow to exclude files by suffix. 
Ensure files ending with `.db` are not scanned.


某些病毒扫描程序会在每次访问文件时对其进行扫描。
不对数据库文件进行病毒扫描对性能来说非常重要。
数据库引擎永远不会将存储在文件中的数据解释为程序，这意味着即使有人将病毒存储在数据库文件中，这也是无害的（当病毒不运行时，它不会传播）。
某些病毒扫描程序允许按后缀排除文件。
确保不扫描以 `.db` 结尾的文件。

---

### Using the Trace Options *使用跟踪选项*

If the performance hot spots are in the database engine, in many cases the performance can be optimized by creating additional indexes, or changing the schema. 
Sometimes the application does not directly generate the SQL statements, for example if an O/R mapping tool is used. 
To view the SQL statements and JDBC API calls, you can use the trace options. 
For more information, see [Using the Trace Options]().


如果性能热点在数据库引擎中，在很多情况下，可以通过创建附加索引或更改架构来优化性能。
有时应用程序不会直接生成 SQL 语句，例如，如果使用 O/R 映射工具。
要查看 SQL 语句和 JDBC API 调用，您可以使用跟踪选项。
有关详细信息，请参阅 [Using the Trace Options]() 。

---

### Index Usage *索引使用*

This database uses indexes to improve the performance of `SELECT`, `UPDATE`, `DELETE`. 
If a column is used in the `WHERE` clause of a query, and if an index exists on this column, then the index can be used. 
Multi-column indexes are used if all or the first columns of the index are used. 
Both equality lookup and range scans are supported. 
Indexes are used to order result sets, but only if the condition uses the same index or no index at all. 
The results are sorted in memory if required. 
Indexes are created automatically for primary key and unique constraints. 
Indexes are also created for foreign key constraints, if required. 
For other columns, indexes need to be created manually using the `CREATE INDEX` statement.


这个数据库使用索引来提高 `SELECT`、 `UPDATE`、 `DELETE` 的性能。
如果在查询的 `WHERE` 子句中使用了列，并且该列上存在索引，则可以使用该索引。
如果使用索引的所有或第一列，则使用多列索引。
支持相等查找和范围扫描。
索引用于对结果集进行排序，但前提是条件使用相同的索引或根本不使用索引。
如果需要，结果会在内存中排序。
索引是为主键和唯一约束自动创建的。
如果需要，还会为外键约束创建索引。
对于其他列，需要使用 `CREATE INDEX` 语句手动创建索引。

---

### Index Hints *索引提示*

If you have determined that H2 is not using the optimal index for your query, you can use index hints to force H2 to use specific indexes.


如果您确定 H2 没有为您的查询使用最佳索引，您可以使用索引提示来强制 H2 使用特定索引。

---

```sql
SELECT * FROM TEST USE INDEX (index_name_1, index_name_2) WHERE X=1
```

Only indexes in the list will be used when choosing an index to use on the given table. 
There is no significance to order in this list.


选择要在给定表上使用的索引时，将仅使用列表中的索引。
在这个列表中排序没有意义。

---

It is possible that no index in the list is chosen, in which case a full table scan will be used.


可能没有选择列表中的索引，在这种情况下将使用全表扫描。

---

An empty list of index names forces a full table scan to be performed.


索引名称的空列表强制执行全表扫描。

---

Each index in the list must exist.


列表中的每个索引都必须存在。

---

### How Data is Stored Internally *数据如何在内部存储*

For persistent databases, if a table is created with a single column primary key of type `BIGINT`, `INT`, `SMALLINT`, `TINYINT`, then the data of the table is organized in this way. 
This is sometimes also called a "clustered index" or "index organized table".


对于持久化数据库，如果一个表是用`BIGINT`、`INT`、`SMALLINT`、`TINYINT`类型的单列主键创建的，那么表的数据就是这样组织的。
这有时也称为“聚集索引”或“索引组织表”。

---

H2 internally stores table data and indexes in the form of b-trees. 
Each b-tree stores entries as a list of unique keys (one or more columns) and data (zero or more columns). 
The table data is always organized in the form of a "data b-tree" with a single column key of type `long`. 
If a single column primary key of type `BIGINT`, `INT`, `SMALLINT`, `TINYINT` is specified when creating the table (or just after creating the table, but before inserting any rows), then this column is used as the key of the data b-tree. 
If no primary key has been specified, if the primary key column is of another data type, or if the primary key contains more than one column, then a hidden auto-increment column of type BIGINT is added to the table, which is used as the key for the data b-tree. 
All other columns of the table are stored within the data area of this data b-tree (except for large `BLOB`, `CLOB` columns, which are stored externally).


H2内部以b-tree的形式存储表数据和索引。
每个 b 树将条目存储为唯一键（一个或多个列）和数据（零个或多个列）的列表。
表数据始终以“数据 b 树”的形式组织，并带有一个类型为 `long` 的单列键。
如果在创建表时（或在创建表之后，但在插入任何行之前）指定了类型为 `BIGINT`、 `INT`、 `SMALLINT`、 `TINYINT` 的单列主键，则该列用作数据 b 树的键。
如果没有指定主键，如果主键列是另一种数据类型，或者主键包含多列，那么表中会添加一个隐藏的 BIGINT 类型的自增列，用作数据 b 树的键。
表的所有其他列都存储在这个数据 b 树的数据区域内（除了大的 `BLOB`、`CLOB` 列，它们是存储在外部的）。

---

For each additional index, one new "index b-tree" is created. 
The key of this b-tree consists of the indexed columns, plus the key of the data b-tree. 
If a primary key is created after the table has been created, or if the primary key contains multiple column, or if the primary key is not of the data types listed above, then the primary key is stored in a new index b-tree.


对于每个附加索引，都会创建一个新的“索引 b 树”。
这个 b 树的键由索引列和数据 b 树的键组成。
如果在创建表后创建主键，或者主键包含多列，或者主键不是上面列出的数据类型，则主键存储在新的索引b-tree中。

---

### Optimizer *优化器*

This database uses a cost based optimizer. 
For simple and queries and queries with medium complexity (less than 7 tables in the join), the expected cost (running time) of all possible plans is calculated, and the plan with the lowest cost is used. 
For more complex queries, the algorithm first tries all possible combinations for the first few tables, and the remaining tables added using a greedy algorithm (this works well for most joins). 
Afterwards a genetic algorithm is used to test at most 2000 distinct plans. Only left-deep plans are evaluated.


该数据库使用基于成本的优化器。
对于简单查询和中等复杂度的查询（join 中少于 7 个表），计算所有可能的计划的预期成本（运行时间），并使用成本最低的计划。
对于更复杂的查询，该算法首先尝试前几个表的所有可能组合，然后使用贪婪算法添加剩余的表（这适用于大多数连接）。
然后使用遗传算法测试最多 2000 个不同的计划。仅评估左深计划。

---

### Expression Optimization *表达优化*

After the statement is parsed, all expressions are simplified automatically if possible. 
Operations are evaluated only once if all parameters are constant. 
Functions are also optimized, but only if the function is constant (always returns the same result for the same parameter values). 
If the `WHERE` clause is always false, then the table is not accessed at all.


语句被解析后，如果可能，所有表达式都会自动简化。
如果所有参数都是常数，则操作仅评估一次。
函数也会被优化，但前提是函数是常量（对于相同的参数值总是返回相同的结果）。
如果 `WHERE` 子句始终为 false，则根本不会访问该表。

---

### COUNT(*) Optimization *COUNT(*) 优化*

If the query only counts all rows of a table, then the data is not accessed. 
However, this is only possible if no `WHERE` clause is used, that means it only works for queries of the form `SELECT COUNT(*) FROM table`.


如果查询只对表的所有行进行计数，则不会访问数据。
然而，这只有在没有使用 `WHERE` 子句时才有可能，这意味着它只适用于 `SELECT COUNT(*) FROM table` 形式的查询。

---

### Updating Optimizer Statistics / Column Selectivity *更新优化器统计列选择性*

When executing a query, at most one index per join can be used. 
If the same table is joined multiple times, for each join only one index is used (the same index could be used for both joins, or each join could use a different index). 
Example: for the query `SELECT * FROM TEST T1, TEST T2 WHERE T1.NAME='A' AND T2.ID=T1.ID`, two index can be used, in this case the index on NAME for T1 and the index on ID for T2.


执行查询时，每个连接最多可以使用一个索引。
如果多次连接同一个表，则每个连接只使用一个索引（两个连接可以使用相同的索引，或者每个连接可以使用不同的索引）。
示例：对于查询 `SELECT * FROM TEST T1, TEST T2 WHERE T1.NAME='A' AND T2.ID=T1.ID` ，可以使用两个索引，在这种情况下，T1 的 NAME 上的索引和 T1 上的索引T2 的 ID。

---

If a table has multiple indexes, sometimes more than one index could be used. 
Example: if there is a table `TEST(ID, NAME, FIRSTNAME)` and an index on each column, then two indexes could be used for the query `SELECT * FROM TEST WHERE NAME='A' AND FIRSTNAME='B'`, the index on NAME or the index on FIRSTNAME. 
It is not possible to use both indexes at the same time. 
Which index is used depends on the selectivity of the column. 
The selectivity describes the 'uniqueness' of values in a column. 
A selectivity of 100 means each value appears only once, and a selectivity of 1 means the same value appears in many or most rows. 
For the query above, the index on NAME should be used if the table contains more distinct names than first names.


如果一张表有多个索引，有时可以使用多个索引。
示例：如果有一个表 `TEST(ID, NAME, FIRSTNAME)` 和每列上的一个索引，那么两个索引可以用于查询 `SELECT * FROM TEST WHERE NAME='A' AND FIRSTNAME='B'` ，NAME 上的索引或 FIRSTNAME 上的索引。
不可能同时使用两个索引。
使用哪个索引取决于列的选择性。
选择性描述了列中值的“唯一性”。
选择性为 100 表示每个值只出现一次，选择性为 1 表示相同的值出现在多行或大多数行中。
对于上面的查询，如果表包含的不同名称多于名字，则应使用 NAME 上的索引。

---

The SQL statement `ANALYZE` can be used to automatically estimate the selectivity of the columns in the tables. 
This command should be run from time to time to improve the query plans generated by the optimizer.


SQL 语句 `ANALYZE` 可用于自动估计表中列的选择性。
应不时运行此命令以改进优化器生成的查询计划。

---

### In-Memory (Hash) Indexes *内存（哈希）索引*

Using in-memory indexes, specially in-memory hash indexes, can speed up queries and data manipulation.


使用内存索引，特别是内存哈希索引，可以加速查询和数据操作。

---

In-memory indexes are automatically used for in-memory databases, but can also be created for persistent databases using `CREATE MEMORY TABLE`. 
In many cases, the rows itself will also be kept in-memory. 
Please note this may cause memory problems for large tables.


内存索引自动用于内存数据库，但也可以使用`CREATE MEMORY TABLE`为持久数据库创建。
在许多情况下，行本身也将保存在内存中。
请注意，这可能会导致大表的内存问题。

---

In-memory hash indexes are backed by a hash table and are usually faster than regular indexes. 
However, hash indexes only supports direct lookup (`WHERE ID = ?`) but not range scan (`WHERE ID < ?`). 
To use hash indexes, use HASH as in: `CREATE UNIQUE HASH INDEX` and `CREATE TABLE ...(ID INT PRIMARY KEY HASH,...)`.


内存哈希索引由哈希表支持，通常比常规索引更快。
但是，哈希索引仅支持直接查找（ `WHERE ID = ?` ）而不支持范围扫描（ `WHERE ID < ?` ）。
要使用哈希索引，请使用 HASH，如： `CREATE UNIQUE HASH INDEX` 和 `CREATE TABLE ...(ID INT PRIMARY KEY HASH,...)` 。

---

### Use Prepared Statements *使用准备好的语句*

If possible, use prepared statements with parameters.


如果可能，请使用带参数的准备好的语句。

---

### Prepared Statements and IN(...) *准备好的语句和 IN(...)*

Avoid generating SQL statements with a variable size IN(...) list. 
Instead, use a prepared statement with arrays as in the following example:


避免生成具有可变大小 IN(...) 列表的 SQL 语句。
相反，使用带有数组的预准备语句，如下例所示：

---

```
PreparedStatement prep = conn.prepareStatement(
    "SELECT * FROM TEST WHERE ID = ANY(?)");
prep.setObject(1, new Object[] { "1", "2" });
ResultSet rs = prep.executeQuery();
```

### Optimization Examples *优化示例*

See `src/test/org/h2/samples/optimizations.sql` for a few examples of queries that benefit from special optimizations built into the database.


请参阅 `src/test/org/h2/samples/optimizations.sql` ，了解一些受益于数据库内置特殊优化的查询示例。

---

### Cache Size and Type *缓存大小和类型*

By default the cache size of H2 is quite small. 
Consider using a larger cache size, or enable the second level soft reference cache. 
See also [Cache Settings]().


默认情况下，H2 的缓存大小非常小。
考虑使用更大的缓存大小，或者启用二级软引用缓存。
另请参阅 [Cache Settings]() 。

---

### Data Types *数据类型*

Each data type has different storage and performance characteristics:


每种数据类型都有不同的存储和性能特征：

---

* The `DECIMAL/NUMERIC` type is slower and requires more storage than the `REAL` and `DOUBLE` types.
* Text types are slower to read, write, and compare than numeric types and generally require more storage.
* See [Large Objects]() for information on `BINARY` vs. `BLOB` and `VARCHAR` vs. `CLOB` performance.
* Parsing and formatting takes longer for the `TIME`, `DATE`, and `TIMESTAMP` types than the numeric types.
* `SMALLINT`/`TINYINT`/`BOOLEAN` are not significantly smaller or faster to work with than `INTEGER` in most modes. 


* `DECIMALNUMERIC` 类型比 `REAL` 和 `DOUBLE` 类型更慢并且需要更多的存储空间。
* 文本类型的读取、写入和比较速度比数字类型慢，并且通常需要更多存储空间。
* 有关 `BINARY` 与 `BLOB` 和 `VARCHAR` 与 `CLOB` 性能的信息，请参阅 [Large Objects]() 。
* 与数字类型相比， `TIME`、 `DATE` 和 `TIMESTAMP` 类型的解析和格式化需要更长的时间。
* 在大多数模式下， `SMALLINT` / `TINYINT` / `BOOLEAN` 并没有比 `INTEGER` 显着更小或更快。

---

### Sorted Insert Optimization *排序插入优化*

To reduce disk space usage and speed up table creation, an optimization for sorted inserts is available. 
When used, b-tree pages are split at the insertion point. 
To use this optimization, add `SORTED` before the `SELECT` statement:


为了减少磁盘空间使用并加快表创建速度，可以对排序插入进行优化。
使用时，b-tree 页面在插入点处被分割。
要使用此优化，请在 `SELECT` 语句之前添加 `SORTED` ：

---

```sql
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR) AS
    SORTED SELECT X, SPACE(100) FROM SYSTEM_RANGE(1, 100);
INSERT INTO TEST
    SORTED SELECT X, SPACE(100) FROM SYSTEM_RANGE(101, 200);
```

## Using the Built-In Profiler *使用内置分析器*

A very simple Java profiler is built-in. 
To use it, use the following template:


内置了一个非常简单的 Java 分析器。
要使用它，请使用以下模板：

---

```
import org.h2.util.Profiler;
Profiler prof = new Profiler();
prof.startCollecting();
// .... some long running process, at least a few seconds
prof.stopCollecting();
System.out.println(prof.getTop(3));
```

## Application Profiling *应用程序分析*

### Analyze First *先分析*

Before trying to optimize performance, it is important to understand where the problem is (what part of the application is slow). 
Blind optimization or optimization based on guesses should be avoided, because usually it is not an efficient strategy. 
There are various ways to analyze an application. Sometimes two implementations can be compared using `System.currentTimeMillis()`. 
But this does not work for complex applications with many modules, and for memory problems.


在尝试优化性能之前，重要的是要了解问题出在哪里（应用程序的哪个部分很慢）。
应该避免盲目优化或基于猜测的优化，因为这通常不是一种有效的策略。
有多种方法可以分析应用程序。有时可以使用 `System.currentTimeMillis()` 比较两个实现。
但这不适用于具有许多模块的复杂应用程序以及内存问题。

---

A simple way to profile an application is to use the built-in profiling tool of java. 
Example:


分析应用程序的一种简单方法是使用 java 的内置分析工具。
例子：

---

`java -Xrunhprof:cpu=samples,depth=16 com.acme.Test`

Unfortunately, it is only possible to profile the application from start to end. 
Another solution is to create a number of full thread dumps. 
To do that, first run `jps -l` to get the process id, and then run `jstack <pid>` or `kill -QUIT <pid>` (Linux) or press `Ctrl+C` (Windows).


不幸的是，只能从头到尾对应用程序进行概要分析。
另一种解决方案是创建多个完整的线程转储。
为此，首先运行 `jps -l` 来获取进程ID，然后运行 `jstack <pid>` 或 `kill -QUIT <pid>` (Linux) 或按 `Ctrl+C` (Windows)。

---

A simple profiling tool is included in H2. 
To use it, the application needs to be changed slightly. 
Example:


H2 中包含一个简单的分析工具。
要使用它，应用程序需要稍作改动。
例子：

---

```
import org.h2.util;
...
Profiler profiler = new Profiler();
profiler.startCollecting();
// application code
System.out.println(profiler.getTop(3));
```

The profiler is built into the H2 Console tool, to analyze databases that open slowly. 
To use it, run the H2 Console, and then click on 'Test Connection'. 
Afterwards, click on "Test successful" and you get the most common stack traces, which helps to find out why it took so long to connect. 
You will only get the stack traces if opening the database took more than a few seconds.


分析器内置于 H2 控制台工具中，用于分析打开缓慢的数据库。
要使用它，请运行 H2 控制台，然后单击“测试连接”。
之后，单击“测试成功”，您将获得最常见的堆栈跟踪，这有助于找出连接需要这么长时间的原因。

---

## Database Profiling *数据库分析*

The `ConvertTraceFile` tool generates SQL statement statistics at the end of the SQL script file. 
The format used is similar to the profiling data generated when using `java -Xrunhprof`. 
For this to work, the trace level needs to be 2 or higher (`TRACE_LEVEL_FILE=2`). 
The easiest way to set the trace level is to append the setting to the database URL, for example: `jdbc:h2:~/test;TRACE_LEVEL_FILE=2` or `jdbc:h2:tcp://localhost/~/test;TRACE_LEVEL_FILE=2`. 
As an example, execute the following script using the H2 Console:


`ConvertTraceFile` 工具在 SQL 脚本文件的末尾生成 SQL 语句统计信息。
使用的格式类似于使用 `java -Xrunhprof` 时生成的分析数据。
为此，跟踪级别需要为 2 或更高（ `TRACE_LEVEL_FILE=2` ）。
设置跟踪级别的最简单方法是将设置附加到数据库 URL，例如： `jdbc:h2:~test;TRACE_LEVEL_FILE=2` 或 `jdbc:h2:tcp:localhost~test;TRACE_LEVEL_FILE=2` 。
例如，使用 H2 控制台执行以下脚本：

---

```sql
SET TRACE_LEVEL_FILE 2;
DROP TABLE IF EXISTS TEST;
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));
@LOOP 1000 INSERT INTO TEST VALUES(?, ?);
SET TRACE_LEVEL_FILE 0;
```

After running the test case, convert the `.trace.db` file using the `ConvertTraceFile` tool. 
The trace file is located in the same directory as the database file.


运行测试用例后，使用 `ConvertTraceFile` 工具转换 `.trace.db` 文件。
跟踪文件与数据库文件位于同一目录中。

---

```shell
java -cp h2*.jar org.h2.tools.ConvertTraceFile
    -traceFile "~/test.trace.db" -script "~/test.sql"
```

The generated file `test.sql` will contain the SQL statements as well as the following profiling data (results vary):


生成的文件 test.sql 将包含 SQL 语句以及以下分析数据（结果各不相同）：

---

```
-----------------------------------------
-- SQL Statement Statistics
-- time: total time in milliseconds (accumulated)
-- count: how many times the statement ran
-- result: total update count or row count
-----------------------------------------
-- self accu    time   count  result sql
--  62%  62%     158    1000    1000 INSERT INTO TEST VALUES(?, ?);
--  37% 100%      93       1       0 CREATE TABLE TEST(ID INT PRIMARY KEY...
--   0% 100%       0       1       0 DROP TABLE IF EXISTS TEST;
--   0% 100%       0       1       0 SET TRACE_LEVEL_FILE 3;
```

## Statement Execution Plans *语句执行计划*

The SQL statement `EXPLAIN` displays the indexes and optimizations the database uses for a statement. 
The following statements support `EXPLAIN`: `SELECT`, `UPDATE`, `DELETE`, `MERGE`, `INSERT`. 
The following query shows that the database uses the primary key index to search for rows:


SQL 语句“EXPLAIN”显示数据库用于语句的索引和优化。
以下语句支持 `EXPLAIN` ： `SELECT`、 `UPDATE`、 `DELETE`、 `MERGE`、 `INSERT` 。
以下查询显示数据库使用主键索引搜索行：

---

```sql
EXPLAIN SELECT * FROM TEST WHERE ID=1;
SELECT
    TEST.ID,
    TEST.NAME
FROM PUBLIC.TEST
    /* PUBLIC.PRIMARY_KEY_2: ID = 1 */
WHERE ID = 1
```

For joins, the tables in the execution plan are sorted in the order they are processed. 
The following query shows the database first processes the table `INVOICE` (using the primary key). 
For each row, it will additionally check that the value of the column `AMOUNT` is larger than zero, and for those rows the database will search in the table `CUSTOMER` (using the primary key). 
The query plan contains some redundancy so it is a valid statement.


对于连接，执行计划中的表按它们的处理顺序排序。
以下查询显示数据库首先处理表 `INVOICE` （使用主键）。
对于每一行，它会额外检查 `AMOUNT` 列的值是否大于零，对于这些行，数据库将在表 `CUSTOMER` 中搜索（使用主键）。
查询计划包含一些冗余，因此它是一个有效的语句。

---

```sql
CREATE TABLE CUSTOMER(ID IDENTITY, NAME VARCHAR);
CREATE TABLE INVOICE(ID IDENTITY,
    CUSTOMER_ID INT REFERENCES CUSTOMER(ID),
    AMOUNT NUMBER);

EXPLAIN SELECT I.ID, C.NAME FROM CUSTOMER C, INVOICE I
WHERE I.ID=10 AND AMOUNT>0 AND C.ID=I.CUSTOMER_ID;

SELECT
    I.ID,
    C.NAME
FROM PUBLIC.INVOICE I
    /* PUBLIC.PRIMARY_KEY_9: ID = 10 */
    /* WHERE (I.ID = 10)
        AND (AMOUNT > 0)
    */
INNER JOIN PUBLIC.CUSTOMER C
    /* PUBLIC.PRIMARY_KEY_5: ID = I.CUSTOMER_ID */
    ON 1=1
WHERE (C.ID = I.CUSTOMER_ID)
    AND ((I.ID = 10)
    AND (AMOUNT > 0))
```

### Displaying the Scan Count *显示扫描计数*

`EXPLAIN ANALYZE` additionally shows the scanned rows per table and pages read from disk per table or index. 
This will actually execute the query, unlike `EXPLAIN` which only prepares it. 
The following query scanned 1000 rows, and to do that had to read 85 pages from the data area of the table. 
Running the query twice will not list the pages read from disk, because they are now in the cache. 
The tableScan means this query doesn't use an index.


`EXPLAIN ANALYZE` 另外显示每个表的扫描行和每个表或索引从磁盘读取的页面。
这将实际执行查询，不像 `EXPLAIN` 只准备它。
以下查询扫描了 1000 行，为此必须从表的数据区读取 85 页。
两次运行查询不会列出从磁盘读取的页面，因为它们现在在缓存中。
tableScan 意味着此查询不使用索引。

---

```sql
EXPLAIN ANALYZE SELECT * FROM TEST;
SELECT
    TEST.ID,
    TEST.NAME
FROM PUBLIC.TEST
    /* PUBLIC.TEST.tableScan */
    /* scanCount: 1000 */
/*
total: 85
TEST.TEST_DATA read: 85 (100%)
*/
```

The cache will prevent the pages are read twice. 
H2 reads all columns of the row unless only the columns in the index are read. 
Except for large CLOB and BLOB, which are not store in the table.


缓存会阻止页面被读取两次。
H2 读取行的所有列，除非仅读取索引中的列。
除了大的 CLOB 和 BLOB，它们不存储在表中。

---

### Special Optimizations *特别优化*

For certain queries, the database doesn't need to read all rows, or doesn't need to sort the result even if `ORDER BY` is used.


对于某些查询，数据库不需要读取所有行，或者即使使用“ORDER BY”也不需要对结果进行排序。

---

For queries of the form `SELECT COUNT(*), MIN(ID), MAX(ID) FROM TEST`, the query plan includes the line `/* direct lookup */` if the data can be read from an index.


对于 `SELECT COUNT(*), MIN(ID), MAX(ID) FROM TEST` 形式的查询，如果可以从索引中读取数据，则查询计划包括 `/* direct lookup */` 行。

---

For queries of the form `SELECT DISTINCT CUSTOMER_ID FROM INVOICE`, the query plan includes the line `/* distinct */` if there is an non-unique or multi-column index on this column, and if this column has a low selectivity.


对于 `SELECT DISTINCT CUSTOMER_ID FROM INVOICE` 形式的查询，如果此列上存在非唯一索引或多列索引，并且此列的选择性较低，则查询计划包括 `/* distinct */` 行。

---

For queries of the form `SELECT * FROM TEST ORDER BY ID`, the query plan includes the line `/* index sorted */` to indicate there is no separate sorting required.


对于 `SELECT * FROM TEST ORDER BY ID` 形式的查询，查询计划包括 `/* index sorted */` 行以指示不需要单独的排序。

---

For queries of the form `SELECT * FROM TEST GROUP BY ID ORDER BY ID`, the query plan includes the line `/* group sorted */` to indicate there is no separate sorting required.


对于 `SELECT * FROM TEST GROUP BY ID ORDER BY ID` 形式的查询，查询计划包括 `/* group sorted */` 行以指示不需要单独的排序。

---

## How Data is Stored and How Indexes Work *数据如何存储以及索引如何工作*

Internally, each row in a table is identified by a unique number, the row id. 
The rows of a table are stored with the row id as the key. 
The row id is a number of type long. 
If a table has a single column primary key of type `INT` or `BIGINT`, then the value of this column is the row id, otherwise the database generates the row id automatically. 
There is a (non-standard) way to access the row id: using the `_ROWID_` pseudo-column:


在内部，表中的每一行都由唯一编号（行 ID）标识。
表的行以行 id 作为键存储。
行 id 是一个 long 类型的数字。
如果表有单列主键类型为 `INT` 或 `BIGINT` ，则该列的值为行id，否则数据库自动生成行id。
有一种（非标准）方式来访问行 ID：使用 `_ROWID_` 伪列：

---

```sql
CREATE TABLE ADDRESS(FIRST_NAME VARCHAR,
    NAME VARCHAR, CITY VARCHAR, PHONE VARCHAR);
INSERT INTO ADDRESS VALUES('John', 'Miller', 'Berne', '123 456 789');
INSERT INTO ADDRESS VALUES('Philip', 'Jones', 'Berne', '123 012 345');
SELECT _ROWID_, * FROM ADDRESS;
```

The data is stored in the database as follows:


数据存储在数据库中如下：

---

| _ROWID_ | FIRST_NAME | NAME | CITY | PHONE |
| ---- | ---- | ---- | ---- | ---- |
| 1 | John | Miller | Berne | 123 456 789 |
| 2 | Philip | Jones | Berne | 123 012 345 |


| _ROWID_ | FIRST_NAME | NAME | CITY | PHONE |
| ---- | ---- | ---- | ---- | ---- |
| 1 | John | Miller | Berne | 123 456 789 |
| 2 | Philip | Jones | Berne | 123 012 345 |

---

Access by row id is fast because the data is sorted by this key. 
Please note the row id is not available until after the row was added (that means, it can not be used in computed columns or constraints). 
If the query condition does not contain the row id (and if no other index can be used), then all rows of the table are scanned. 
A table scan iterates over all rows in the table, in the order of the row id. 
To find out what strategy the database uses to retrieve the data, use `EXPLAIN SELECT`:


按行 id 访问速度很快，因为数据是按此键排序的。
请注意，行 id 在添加行之后才可用（这意味着它不能用于计算列或约束）。
如果查询条件不包含行 id（并且如果没有其他索引可以使用），则扫描表的所有行。
表扫描按行 id 的顺序遍历表中的所有行。
要找出数据库用于检索数据的策略，请使用 `EXPLAIN SELECT` ：

---

```sql
SELECT * FROM ADDRESS WHERE NAME = 'Miller';

EXPLAIN SELECT PHONE FROM ADDRESS WHERE NAME = 'Miller';
SELECT
    PHONE
FROM PUBLIC.ADDRESS
    /* PUBLIC.ADDRESS.tableScan */
WHERE NAME = 'Miller';
```

### Indexes *索引*

An index internally is basically just a table that contains the indexed column(s), plus the row id:


内部索引基本上只是一个包含索引列和行 ID 的表：

---

```sql
CREATE INDEX INDEX_PLACE ON ADDRESS(CITY, NAME, FIRST_NAME);
```

In the index, the data is sorted by the indexed columns. 
So this index contains the following data:


在索引中，数据按索引列排序。
所以这个索引包含以下数据：

---

| CITY | NAME | FIRST_NAME | _ROWID_ |
| ---- | ---- | ----- | ---- |
| Berne | Jones | Philip | 2 |
| Berne | Miller | John | 1 |

When the database uses an index to query the data, it searches the index for the given data, and (if required) reads the remaining columns in the main data table (retrieved using the row id). 
An index on city, name, and first name (multi-column index) allows to quickly search for rows when the city, name, and first name are known. 
If only the city and name, or only the city is known, then this index is also used (so creating an additional index on just the city is not needed). 
This index is also used when reading all rows, sorted by the indexed columns. 
However, if only the first name is known, then this index is not used:


当数据库使用索引查询数据时，它会搜索给定数据的索引，并且（如果需要）读取主数据表中的剩余列（使用行 id 检索）。
城市、姓名和名字的索引（多列索引）允许在城市、姓名和名字已知时快速搜索行。
如果只知道城市和名称，或者只知道城市，那么也会使用此索引（因此不需要仅针对城市创建附加索引）。
读取所有行时也使用此索引，按索引列排序。
但是，如果只知道名字，则不使用此索引：

---

```sql
EXPLAIN SELECT PHONE FROM ADDRESS
    WHERE CITY = 'Berne' AND NAME = 'Miller'
    AND FIRST_NAME = 'John';
SELECT
    PHONE
FROM PUBLIC.ADDRESS
    /* PUBLIC.INDEX_PLACE: FIRST_NAME = 'John'
        AND CITY = 'Berne'
        AND NAME = 'Miller'
     */
WHERE (FIRST_NAME = 'John')
    AND ((CITY = 'Berne')
    AND (NAME = 'Miller'));

EXPLAIN SELECT PHONE FROM ADDRESS WHERE CITY = 'Berne';
SELECT
    PHONE
FROM PUBLIC.ADDRESS
    /* PUBLIC.INDEX_PLACE: CITY = 'Berne' */
WHERE CITY = 'Berne';

EXPLAIN SELECT * FROM ADDRESS ORDER BY CITY, NAME, FIRST_NAME;
SELECT
    ADDRESS.FIRST_NAME,
    ADDRESS.NAME,
    ADDRESS.CITY,
    ADDRESS.PHONE
FROM PUBLIC.ADDRESS
    /* PUBLIC.INDEX_PLACE */
ORDER BY 3, 2, 1
/* index sorted */;

EXPLAIN SELECT PHONE FROM ADDRESS WHERE FIRST_NAME = 'John';
SELECT
    PHONE
FROM PUBLIC.ADDRESS
    /* PUBLIC.ADDRESS.tableScan */
WHERE FIRST_NAME = 'John';
```

If your application often queries the table for a phone number, then it makes sense to create an additional index on it:


如果您的应用程序经常在表中查询电话号码，那么在其上创建附加索引是有意义的：

---

```sql
CREATE INDEX IDX_PHONE ON ADDRESS(PHONE);
```

This index contains the phone number, and the row id:


此索引包含电话号码和行 ID：

---

| PHONE | _ROWID_ |
| ---- | ---- |
| 123 012 345 | 2 |
| 123 456 789 | 1 |

### Using Multiple Indexes *使用多个索引*

Within a query, only one index per logical table is used. 
Using the condition `PHONE = '123 567 789' OR CITY = 'Berne'` would use a table scan instead of first using the index on the phone number and then the index on the city. 
It makes sense to write two queries and combine then using `UNION`. 
In this case, each individual query uses a different index:


在查询中，每个逻辑表仅使用一个索引。
使用条件 `PHONE = '123 567 789' OR CITY = 'Berne'` 将使用表扫描，而不是首先使用电话号码的索引，然后使用城市的索引。
编写两个查询并组合然后使用 `UNION` 是有意义的。
在这种情况下，每个单独的查询使用不同的索引：

---

```sql
EXPLAIN SELECT NAME FROM ADDRESS WHERE PHONE = '123 567 789'
UNION SELECT NAME FROM ADDRESS WHERE CITY = 'Berne';

(SELECT
    NAME
FROM PUBLIC.ADDRESS
    /* PUBLIC.IDX_PHONE: PHONE = '123 567 789' */
WHERE PHONE = '123 567 789')
UNION
(SELECT
    NAME
FROM PUBLIC.ADDRESS
    /* PUBLIC.INDEX_PLACE: CITY = 'Berne' */
WHERE CITY = 'Berne')
```

## Fast Database Import *快速数据库导入*

To speed up large imports, consider using the following options temporarily:


要加速大型导入，请考虑暂时使用以下选项：

---

* `SET LOG 0` (disabling the transaction log)
* `SET CACHE_SIZE` (a large cache is faster)
* `SET LOCK_MODE 0` (disable locking)
* `SET UNDO_LOG 0` (disable the session undo log) 


* `SET LOG 0` （禁用事务日志）
* `SET CACHE_SIZE` （大缓存更快）
* `SET LOCK_MODE 0` （禁用锁定）
* `SET UNDO_LOG 0` （禁用会话撤销日志）

---

These options can be set in the database URL: `jdbc:h2:~/test;LOG=0;CACHE_SIZE=65536;LOCK_MODE=0;UNDO_LOG=0`. 
Most of those options are not recommended for regular use, that means you need to reset them after use.


这些选项可以在数据库 URL 中设置： `jdbc:h2:~/test;LOG=0;CACHE_SIZE=65536;LOCK_MODE=0;UNDO_LOG=0` 。
大多数这些选项不建议经常使用，这意味着您需要在使用后重置它们。

---

If you have to import a lot of rows, use a PreparedStatement or use CSV import. 
Please note that `CREATE TABLE(...) ... AS SELECT ...` is faster than `CREATE TABLE(...); INSERT INTO ... SELECT ...`. 


如果您必须导入很多行，请使用 PreparedStatement 或使用 CSV 导入。
请注意， `CREATE TABLE(...) ... AS SELECT ...` 比 `CREATE TABLE(...); INSERT INTO ... SELECT ...` 。
---
