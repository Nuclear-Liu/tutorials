## Multiple Connections *多个连接*

### Opening Multiple Databases at the Same Time *同时打开多个数据库*

An application can open multiple databases at the same time, including multiple connections to the same database.
The number of open database is only limited by the memory available.


一个应用程序可以同时打开多个数据库，包括到同一个数据库的多个连接。
打开数据库的数量仅受可用内存的限制。

---

### Multiple Connections to the Same Database: Client/Server *到同一数据库的多个连接： Client / Server*

If you want to access the same database at the same time from different processes or computers, you need to use the client / server mode.
In this case, one process acts as the server, and the other processes (that could reside on other computers as well) connect to the server via TCP/IP (or TLS over TCP/IP for improved security).


如果要从不同的进程或计算机同时访问同一个数据库，则需要使用客户端服务器模式。
在这种情况下，一个进程充当服务器，其他进程（也可以驻留在其他计算机上）通过 TCPIP（或 TCPIP 上的 TLS 以提高安全性）连接到服务器。

---

### Multithreading Support *多线程支持*

This database is multithreading-safe.
If an application is multi-threaded, it does not need to worry about synchronizing access to the database.
An application should normally use one connection per thread.
This database synchronizes access to the same connection, but other databases may not do this.
To get higher concurrency, you need to use multiple connections.


该数据库是多线程安全的。
如果应用程序是多线程的，则无需担心同步访问数据库。
应用程序通常应该为每个线程使用一个连接。
此数据库同步访问同一连接，但其他数据库可能不会这样做。
为了获得更高的并发性，您需要使用多个连接。

---

An application can use multiple threads that access the same database at the same time.
With default MVStore engine threads that use different connections can use the database concurrently.
With PageStore engine requests to the same database are synchronized, that means that if one thread executes a long running query, the other threads need to wait.


一个应用程序可以使用多个线程同时访问同一个数据库。
使用不同连接的默认 MVStore 引擎线程可以同时使用数据库。
由于 PageStore 引擎对同一数据库的请求是同步的，这意味着如果一个线程执行长时间运行的查询，其他线程需要等待。

---

### Locking, Lock-Timeout, Deadlocks *锁定，锁定超时，死锁*

Please note MVCC is enabled in version 1.4.x by default, when using the MVStore.
In this case, table level locking is not used.
If [multi-version concurrency]() is not used, the database uses table level locks to give each connection a consistent state of the data.
There are two kinds of locks: read locks (shared locks) and write locks (exclusive locks).
All locks are released when the transaction commits or rolls back.
When using the default transaction isolation level 'read committed', read locks are already released after each statement.


请注意，当使用 MVStore 时，MVCC 在 1.4.x 版本中默认启用。
在这种情况下，不使用表级锁定。
如果不使用 [multi-version concurrency]() ，则数据库使用表级锁来给每个连接一个一致的数据状态。
锁有两种：读锁（共享锁）和写锁（排他锁）。
当使用默认的事务隔离级别 'read committed' 时，读锁已经在每条语句之后被释放。

---

If a connection wants to reads from a table, and there is no write lock on the table, then a read lock is added to the table.
If there is a write lock, then this connection waits for the other connection to release the lock.
If a connection cannot get a lock for a specified time, then a lock timeout exception is thrown.


如果一个连接想要从一个表中读取，并且该表上没有写锁，那么就会给该表添加一个读锁。
如果存在写锁，则此连接等待另一个连接释放锁。
如果连接在指定时间内无法获得锁定，则会引发锁定超时异常。

---

Usually, `SELECT` statements will generate read locks.
This includes subqueries.
Statements that modify data use write locks.
It is also possible to lock a table exclusively without modifying data, using the statement `SELECT ... FOR UPDATE`.
The statements `COMMIT` and `ROLLBACK` releases all open locks.
The commands `SAVEPOINT` and `ROLLBACK TO SAVEPOINT` don't affect locks.
The locks are also released when the autocommit mode changes, and for connections with autocommit set to true (this is the default), locks are released after each statement.
The following statements generate locks:


通常， `SELECT` 语句会产生读锁。
这包括子查询。
修改数据的语句使用写锁。
也可以使用语句 `SELECT ... FOR UPDATE` 以独占方式锁定表而不修改数据。
语句 `COMMIT` 和 `ROLLBACK` 释放所有打开的锁。
命令 `SAVEPOINT` 和 `ROLLBACK TO SAVEPOINT` 不影响锁。
自动提交模式更改时也会释放锁，对于自动提交设置为 true（这是默认设置）的连接，在每个语句之后释放锁。
以下语句生成锁：

---

| Type of Lock | SQL Statement |
| ---- | ---- |
| Read | SELECT * FROM TEST; CALL SELECT MAX(ID) FROM TEST; SCRIPT; |
| Write | SELECT * FROM TEST WHERE 1=0 FOR UPDATE; |
| Write | INSERT INTO TEST VALUES(1, 'Hello'); INSERT INTO TEST SELECT * FROM TEST; UPDATE TEST SET NAME='Hi'; DELETE FROM TEST; |
| Write | ALTER TABLE TEST ...; CREATE INDEX ... ON TEST ...; DROP INDEX ...; |


| 锁类型 | SQL Statement |
| ---- | ---- |
| Read | SELECT * FROM TEST; CALL SELECT MAX(ID) FROM TEST; SCRIPT; |
| Write | SELECT * FROM TEST WHERE 1=0 FOR UPDATE; |
| Write | INSERT INTO TEST VALUES(1, 'Hello'); INSERT INTO TEST SELECT * FROM TEST; UPDATE TEST SET NAME='Hi'; DELETE FROM TEST; |
| Write | ALTER TABLE TEST ...; CREATE INDEX ... ON TEST ...; DROP INDEX ...; |

---

The number of seconds until a lock timeout exception is thrown can be set separately for each connection using the SQL command `SET LOCK_TIMEOUT <milliseconds>`.
The initial lock timeout (that is the timeout used for new connections) can be set using the SQL command `SET DEFAULT_LOCK_TIMEOUT <milliseconds>`.
The default lock timeout is persistent.


可以使用 SQL 命令 `SET LOCK_TIMEOUT <milliseconds>` 为每个连接单独设置直到抛出锁定超时异常的秒数。
可以使用 SQL 命令 `SET DEFAULT_LOCK_TIMEOUT <milliseconds>` 设置初始锁定超时（即用于新连接的超时）。
默认锁定超时是持久的。

---

### Avoiding Deadlocks

To avoid deadlocks, ensure that all transactions lock the tables in the same order (for example in alphabetical order), and avoid upgrading read locks to write locks.
Both can be achieved using explicitly locking tables using `SELECT ... FOR UPDATE`.


为避免死锁，请确保所有事务以相同的顺序（例如按字母顺序）锁定表，并避免将读锁升级为写锁。
两者都可以通过使用 `SELECT ... FOR UPDATE` 显式锁定表来实现。

---

Note that delete, insert and update operations issue table level locks with PageStore engine, but does not issue them with default MVStore engine. 


请注意，删除、插入和更新操作会使用 PageStore 引擎发出表级锁，但不会使用默认的 MVStore 引擎发出它们。

---
