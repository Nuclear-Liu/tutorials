## Transaction Isolation *事务隔离*

Please note that most data definition language (DDL) statements, such as "create table", commit the current transaction.
See the [Commands]() for details.


请注意，大多数数据定义语言 (DDL) 语句，例如 "create table" ，都会提交当前事务。
有关详细信息，请参阅 [Commands]() 。

---

Transaction isolation is provided for all data manipulation language (DML) statements.


为所有数据操作语言 (DML) 语句提供事务隔离。

---

The default MVStore engine supports read uncommitted, read committed, repeatable read, snapshot, and serializable (partially, see below) isolation levels:


默认的 MVStore 引擎支持读未提交、读提交、可重复读、快照和可序列化（部分见下文）隔离级别：

---

* Read uncommitted

  Dirty reads, non-repeatable reads, and phantom reads are possible.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ UNCOMMITTED`

* Read committed

  This is the default level.
  Dirty reads aren't possible; non-repeatable reads and phantom reads are possible.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ COMMITTED`

* Repeatable read

  Dirty reads and non-repeatable reads aren't possible, phantom reads are possible.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL REPEATABLE READ`

* Snapshot

  Dirty reads, non-repeatable reads, and phantom reads aren't possible.
  This isolation level is very expensive in databases with many tables.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SNAPSHOT`

* Serializable

  Dirty reads, non-repeatable reads, and phantom reads aren't possible.
  Note that this isolation level in H2 currently doesn't ensure equivalence of concurrent and serializable execution of transactions that perform write operations.
  This isolation level is very expensive in databases with many tables.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE`


* 读未提交
  
  脏读、不可重复读和幻读都是可能的。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ UNCOMMITTED`
  
* 读已提交

  这是默认级别。
  脏读是不可能的；不可重复读和幻读是可能的。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ COMMITTED`
  
* 可重复读取

  脏读和不可重复读是不可能的，幻读是可能的。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL REPEATABLE READ`
  
* 快照

  脏读、不可重复读和幻读是不可能的。
  在具有许多表的数据库中，此隔离级别非常昂贵。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SNAPSHOT`
  
* 可序列化

  脏读、不可重复读和幻读是不可能的。
  请注意，H2 中的此隔离级别当前并不能确保执行写操作的事务的并发和可序列化执行的等效性。
  在具有许多表的数据库中，此隔离级别非常昂贵。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE`

---

The PageStore engine supports read uncommitted, read committed, and serializable isolation levels:


PageStore 引擎支持读未提交、读提交和可序列化隔离级别：

---

* Read uncommitted

  This level means that transaction isolation is disabled.
  This level is not supported by PageStore engine if multi-threaded mode is enabled.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ UNCOMMITTED`

* Read committed

  This is the default level.
  Read locks are released immediately after executing the statement, but write locks are kept until the transaction commits.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ COMMITTED`

* Serializable

  Both read locks and write locks are kept until the transaction commits.
  To enable, execute the SQL statement `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE`


* 读未提交

  此级别意味着禁用事务隔离。
  如果启用了多线程模式，则 PageStore 引擎不支持此级别。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ UNCOMMITTED`
  
* 读已提交

  这是默认级别。
  执行语句后立即释放读锁，但保留写锁直到事务提交。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL READ COMMITTED`
  
* 可序列化

  读锁和写锁都会保留到事务提交。
  要启用，请执行 SQL 语句 `SET SESSION CHARACTERISTICS AS TRANSACTION ISOLATION LEVEL SERIALIZABLE`
  
---

If repeatable read isolation level is requested when using a PageStore engine it is replaced with serializable isolation level.


如果在使用 PageStore 引擎时请求可重复读取隔离级别，则将其替换为可序列化隔离级别。

---

* Dirty reads

  Means a connection can read uncommitted changes made by another connection.

  Possible with: read uncommitted.

* Non-repeatable reads

  A connection reads a row, another connection changes a row and commits, and the first connection re-reads the same row and gets the new result.

  Possible with: read uncommitted, read committed.

* Phantom reads

  A connection reads a set of rows using a condition, another connection inserts a row that falls in this condition and commits, then the first connection re-reads using the same condition and gets the new row.

  Possible with: read uncommitted, read committed, repeatable read.


* 脏读

  意味着一个连接可以读取另一个连接所做的未提交的更改。

  可能与：读取未提交。

* 不可重复读取

  一个连接读取一行，另一个连接更改一行并提交，第一个连接重新读取同一行并获得新结果。

  可能有：读未提交，读已提交。
  
* 幻读

  一个连接使用条件读取一组行，另一个连接插入符合此条件的行并提交，然后第一个连接使用相同的条件重新读取并获取新行。

  可能有：读未提交、读已提交、可重复读。
  
---


### Multi-Version Concurrency Control (MVCC)

With default MVStore engine delete, insert and update operations only issue a shared lock on the table.
An exclusive lock is still used when adding or removing columns or when dropping the table.
Connections only 'see' committed data, and own changes.
That means, if connection A updates a row but doesn't commit this change yet, connection B will see the old value.
Only when the change is committed, the new value is visible by other connections (read committed).
If multiple connections concurrently try to lock or update the same row, the database waits until it can apply the change, but at most until the lock timeout expires.


使用默认的 MVStore 引擎删除、插入和更新操作仅在表上发出共享锁。
添加或删除列或删除表时仍使用排他锁。
连接仅“看到”提交的数据，并拥有自己的更改。
这意味着，如果连接 A 更新了一行但尚未提交此更改，则连接 B 将看到旧值。
只有在提交更改时，其他连接才能看到新值（已提交读）。
如果多个连接同时尝试锁定或更新同一行，则数据库会等待直到它可以应用更改，但最多直到锁定超时到期。

---

### Table Level Locking (PageStore engine)

With PageStore engine to make sure all connections only see consistent data, table level locking is used.
This mechanism does not allow high concurrency, but is very fast. Shared locks and exclusive locks are supported.
Before reading from a table, the database tries to add a shared lock to the table (this is only possible if there is no exclusive lock on the object by another connection).
If the shared lock is added successfully, the table can be read.
It is allowed that other connections also have a shared lock on the same object.
If a connection wants to write to a table (update or delete a row), an exclusive lock is required.
To get the exclusive lock, other connection must not have any locks on the object.
After the connection commits, all locks are released. 
This database keeps all locks in memory.
When a lock is released, and multiple connections are waiting for it, one of them is picked at random.


使用 PageStore 引擎确保所有连接只看到一致的数据，使用表级锁定。
这种机制不允许高并发，但速度非常快。支持共享锁和排他锁。
在从表中读取之前，数据库尝试向表添加共享锁（这只有在另一个连接没有对对象进行排他锁的情况下才有可能）。
如果共享锁添加成功，则可以读取该表。
允许其他连接在同一对象上也有共享锁。
如果连接想要写入表（更新或删除行），则需要排他锁。
要获得排他锁，其他连接不能对对象有任何锁。
连接提交后，所有锁都被释放。
该数据库将所有锁保存在内存中。
当一个锁被释放，并且有多个连接在等待它时，随机选择其中一个。

---

### Lock Timeout

If a connection cannot get a lock on an object, the connection waits for some amount of time (the lock timeout).
During this time, hopefully the connection holding the lock commits and it is then possible to get the lock.
If this is not possible because the other connection does not release the lock for some time, the unsuccessful connection will get a lock timeout exception.
The lock timeout can be set individually for each connection.


如果连接无法获得对象的锁定，则连接会等待一段时间（锁定超时）。
在此期间，希望持有锁的连接提交，然后就有可能获得锁。
如果由于其他连接在一段时间内没有释放锁而无法做到这一点，则不成功的连接将获得锁超时异常。
可以为每个连接单独设置锁定超时。

---
