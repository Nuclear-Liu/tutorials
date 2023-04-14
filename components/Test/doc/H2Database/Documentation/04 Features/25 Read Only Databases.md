## Read Only Databases *只读数据库*

If the database files are read-only, then the database is read-only as well.
It is not possible to create new tables, add or modify data in this database.
Only `SELECT` and `CALL` statements are allowed.
To create a read-only database, close the database.
Then, make the database file read-only.
When you open the database now, it is read-only.
There are two ways an application can find out whether database is read-only:
by calling `Connection.isReadOnly()` or by executing the SQL statement `CALL READONLY()`.


如果数据库文件是只读的，那么数据库也是只读的。
无法在此数据库中创建新表、添加或修改数据。
只允许使用 `SELECT` 和 `CALL` 语句。
要创建只读数据库，请关闭该数据库。
然后，将数据库文件设为只读。
现在打开数据库时，它是只读的。
应用程序可以通过两种方式确定数据库是否为只读：
通过调用 `Connection.isReadOnly()` 或通过执行SQL语句 `CALL READONLY()` 。

---

Using the [Custom Access Mode]() `r` the database can also be opened in read-only mode, even if the database file is not read only. 


使用 [Custom Access Mode]() `r` 数据库也可以以只读模式打开，即使数据库文件不是只读的。

---
