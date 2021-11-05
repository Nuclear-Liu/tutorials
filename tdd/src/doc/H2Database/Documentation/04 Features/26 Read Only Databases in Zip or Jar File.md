## Read Only Databases in Zip or Jar File *Zip 或 Jar 文件中的只读数据库*

To create a read-only database in a zip file, first create a regular persistent database, and then create a backup.
The database must not have pending changes, that means you need to close all connections to the database first.
To speed up opening the read-only database and running queries, the database should be closed using `SHUTDOWN DEFRAG`.
If you are using a database named `test`, an easy way to create a zip file is using the `Backup` tool.
You can start the tool from the command line, or from within the H2 Console (Tools - Backup).
Please note that the database must be closed when the backup is created.
Therefore, the SQL statement `BACKUP TO` can not be used.


要在 zip 文件中创建只读数据库，请首先创建一个常规持久性数据库，然后创建一个备份。
数据库不能有挂起的更改，这意味着您需要先关闭与数据库的所有连接。
为了加快打开只读数据库和运行查询的速度，应该使用“SHUTDOWN DEFRAG”关闭数据库。
如果您使用名为 `test` 的数据库，创建 zip 文件的一种简单方法是使用 `Backup` 工具。
您可以从命令行或从 H2 控制台（工具 - 备份）中启动该工具。
请注意，创建备份时必须关闭数据库。
因此，不能使用 SQL 语句 `BACKUP TO` 。

---

When the zip file is created, you can open the database in the zip file using the following database URL:


创建 zip 文件后，您可以使用以下数据库 URL 打开 zip 文件中的数据库：

---

`jdbc:h2:zip:~/data.zip!/test`

Databases in zip files are read-only.
The performance for some queries will be slower than when using a regular database, because random access in zip files is not supported (only streaming).
How much this affects the performance depends on the queries and the data.
The database is not read in memory; therefore large databases are supported as well.
The same indexes are used as when using a regular database.


zip 文件中的数据库是只读的。
某些查询的性能会比使用常规数据库时慢，因为不支持 zip 文件中的随机访问（仅流式传输）。
这对性能的影响有多大取决于查询和数据。
数据库不在内存中读取；因此也支持大型数据库。
使用与使用常规数据库时相同的索引。

---

If the database is larger than a few megabytes, performance is much better if the database file is split into multiple smaller files, because random access in compressed files is not possible.
See also the sample application [ReadOnlyDatabaseInZip]().


如果数据库大于几兆，如果将数据库文件拆分为多个较小的文件，性能会好得多，因为无法在压缩文件中进行随机访问。
另请参阅示例应用程序 [ReadOnlyDatabaseInZip]() 。

---

### Opening a Corrupted Database *打开损坏的数据库*

If a database cannot be opened because the boot info (the SQL script that is run at startup) is corrupted, then the database can be opened by specifying a database event listener.
The exceptions are logged, but opening the database will continue.


如果由于启动信息（启动时运行的 SQL 脚本）损坏而无法打开数据库，则可以通过指定数据库事件侦听器打开数据库。
将记录异常，但将继续打开数据库。

---
