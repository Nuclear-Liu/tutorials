## Two Phase Commit *两阶段提交*

The two phase commit protocol is supported.
2-phase-commit works as follows:


支持两阶段提交协议。
两阶段提交的工作原理如下：

---

* Autocommit needs to be switched off
* A transaction is started, for example by inserting a row
* The transaction is marked 'prepared' by executing the SQL statement `PREPARE COMMIT transactionName`
* The transaction can now be committed or rolled back
* If a problem occurs before the transaction was successfully committed or rolled back (for example because a network problem occurred), the transaction is in the state 'in-doubt'
* When re-connecting to the database, the in-doubt transactions can be listed with `SELECT * FROM INFORMATION_SCHEMA.IN_DOUBT`
* Each transaction in this list must now be committed or rolled back by executing `COMMIT TRANSACTION transactionName` or `ROLLBACK TRANSACTION transactionName`
* The database needs to be closed and re-opened to apply the changes 


* 需要关闭自动提交
* 事务开始，例如通过插入一行
* 通过执行 SQL 语句 `PREPARE COMMIT transactionName` 将事务标记为 “已准备好”
* 现在可以提交或回滚事务
* 如果在事务成功提交或回滚之前出现问题（例如因为发生网络问题），则事务处于“不确定”状态
* 重新连接到数据库时，可以使用 `SELECT * FROM INFORMATION_SCHEMA.IN_DOUBT` 列出有疑问的事务
* 现在必须通过执行 `COMMIT TRANSACTION transactionName` 或 `ROLLBACK TRANSACTION transactionName` 来提交或回滚此列表中的每个事务
* 数据库需要关闭并重新打开以应用更改

---
