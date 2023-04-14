## Logging and Recovery *日志记录和恢复*

Whenever data is modified in the database and those changes are committed, the changes are written to the transaction log (except for in-memory objects).
The changes to the main data area itself are usually written later on, to optimize disk access.
If there is a power failure, the main data area is not up-to-date, but because the changes are in the transaction log, the next time the database is opened, the changes are re-applied automatically. 


每当修改数据库中的数据并提交这些更改时，这些更改都会写入事务日志（内存对象除外）。
对主数据区本身的更改通常稍后写入，以优化磁盘访问。
如果断电，主数据区不是最新的，但因为更改在事务日志中，下次打开数据库时，更改会自动重新应用。

---
