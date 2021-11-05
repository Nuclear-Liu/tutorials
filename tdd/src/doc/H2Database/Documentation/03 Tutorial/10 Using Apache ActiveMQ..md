## Using Apache ActiveMQ *使用 Apache ActiveMQ*

When using H2 as the backend database for Apache ActiveMQ, please use the `TransactDatabaseLocker` instead of the default locking mechanism.
Otherwise the database file will grow without bounds.
The problem is that the default locking mechanism uses an uncommitted `UPDATE` transaction, which keeps the transaction log from shrinking (causes the database file to grow).
Instead of using an `UPDATE` statement, the `TransactDatabaseLocker` uses `SELECT ... FOR UPDATE` which is not problematic.
To use it, change the ApacheMQ configuration element `<jdbcPersistenceAdapter>` element, property `databaseLocker="org.apache.activemq.store.jdbc.adapter.TransactDatabaseLocker"`.
However, using the MVCC mode will again result in the same problem.
Therefore, please do not use the MVCC mode in this case.
Another (more dangerous) solution is to set `useDatabaseLock` to false.


当使用 H2 作为 Apache ActiveMQ 的后端数据库时，请使用 `TransactDatabaseLocker` 而不是默认的锁定机制。
否则数据库文件将无限增长。
问题是默认锁定机制使用未提交的 `UPDATE` 事务，这可以防止事务日志缩小（导致数据库文件增长）。
而不是使用 `UPDATE` 语句， `TransactDatabaseLocker` 使用 `SELECT ... FOR UPDATE` 这没有问题。
要使用它，请更改 ApacheMQ 配置元素 `<jdbcPersistenceAdapter>` 元素，属性 `databaseLocker="org.apache.activemq.store.jdbc.adapter.TransactDatabaseLocker"` 。
但是，使用 MVCC 模式将再次导致相同的问题。
因此，请不要在这种情况下使用 MVCC 模式。
另一个（更危险的）解决方案是将 `useDatabaseLock` 设置为 false。

----
