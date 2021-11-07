## ACID *ACID*

In the database world, ACID stands for:


在数据库世界中，ACID 代表：

---

* Atomicity: transactions must be atomic, meaning either all tasks are performed or none.
* Consistency: all operations must comply with the defined constraints.
* Isolation: transactions must be isolated from each other.
* Durability: committed transaction will not be lost.


* 原子性：事务必须是原子的，这意味着要么执行所有任务，要么不执行。
* 一致性：所有操作都必须符合定义的约束。
* 隔离：事务之间必须相互隔离。
* 持久性：提交的事务不会丢失。

---

### Atomicity *原子性*

Transactions in this database are always atomic.


此数据库中的事务始终是原子的。

---

### Consistency *一致性*

By default, this database is always in a consistent state.
Referential integrity rules are enforced except when explicitly disabled.


默认情况下，此数据库始终处于一致状态。
除非明确禁用，否则会强制执行参照完整性规则。

---

### Isolation *隔离*

For H2, as with most other database systems, the default isolation level is 'read committed'.
This provides better performance, but also means that transactions are not completely isolated.
H2 supports the transaction isolation levels 'read uncommitted', 'read committed', 'repeatable read', and 'serializable'.


对于 H2，与大多数其他数据库系统一样，默认隔离级别是 'read committed' 。
这提供了更好的性能，但也意味着事务不是完全隔离的。
H2 支持事务隔离级别 'read uncommitted' 、 'read committed' 、 'repeatable read' 和 'serializable'。

---

### Durability *耐用性*

This database does not guarantee that all committed transactions survive a power failure.
Tests show that all databases sometimes lose transactions on power failure (for details, see below).
Where losing transactions is not acceptable, a laptop or UPS (uninterruptible power supply) should be used.
If durability is required for all possible cases of hardware failure, clustering should be used, such as the H2 clustering mode.


此数据库不保证所有已提交的事务在断电后都能幸免于难。
测试表明，所有数据库有时会在断电时丢失事务（有关详细信息，请参见下文）。
在不能接受丢失交易的情况下，应使用笔记本电脑或 UPS（不间断电源）。
如果所有可能的硬件故障情况都需要持久性，则应使用集群，例如 H2 集群模式。

---
