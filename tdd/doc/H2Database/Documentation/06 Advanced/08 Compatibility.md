## Compatibility *兼容性*

This database is (up to a certain point) compatible to other databases such as HSQLDB, MySQL and PostgreSQL.
There are certain areas where H2 is incompatible.


该数据库（在一定程度上）与其他数据库兼容，例如 HSQLDB 、 MySQL 和 PostgreSQL 。
在某些领域，H2 不相容。

---

### Transaction Commit when Autocommit is On *自动提交开启时的事务提交*

At this time, this database engine commits a transaction (if autocommit is switched on) just before returning the result.
For a query, this means the transaction is committed even before the application scans through the result set, and before the result set is closed.
Other database engines may commit the transaction in this case when the result set is closed.


这时，这个数据库引擎在返回结果之前提交一个事务（如果自动提交被打开）。
对于查询，这意味着即使在应用程序扫描结果集之前以及结果集关闭之前，事务已提交。
在这种情况下，当结果集关闭时，其他数据库引擎可能会提交事务。

---
