## Closing a Database *关闭数据库*

### Delayed Database Closing *延迟关闭数据库*

Usually, a database is closed when the last connection to it is closed.
In some situations this slows down the application, for example when it is not possible to keep at least one connection open.
The automatic closing of a database can be delayed or disabled with the SQL statement `SET DB_CLOSE_DELAY <seconds>`.
The parameter <seconds> specifies the number of seconds to keep a database open after the last connection to it was closed.
The following statement will keep a database open for 10 seconds after the last connection was closed:


通常，数据库在最后一个连接关闭时关闭。
在某些情况下，这会减慢应用程序的速度，例如当无法保持至少一个连接打开时。
可以使用 SQL 语句 `SET DB_CLOSE_DELAY <seconds>` 延迟或禁用数据库的自动关闭。
参数 <seconds> 指定数据库在最后一个连接关闭后保持打开的秒数。
以下语句将使数据库在最后一个连接关闭后保持打开状态 10 秒：

```sql
SET DB_CLOSE_DELAY 10
```

The value -1 means the database is not closed automatically.
The value 0 is the default and means the database is closed when the last connection is closed.
This setting is persistent and can be set by an administrator only.
It is possible to set the value in the database URL: `jdbc:h2:~/test;DB_CLOSE_DELAY=10`.


值 -1 表示不会自动关闭数据库。
值 0 是默认值，表示在最后一个连接关闭时关闭数据库。
此设置是永久性的，只能由管理员设置。
可以在数据库 URL 中设置值： `jdbc:h2:~test;DB_CLOSE_DELAY=10` 。

---

### Don't Close a Database when the VM Exits *VM 退出时不要关闭数据库*

By default, a database is closed when the last connection is closed.
However, if it is never closed, the database is closed when the virtual machine exits normally, using a shutdown hook.
In some situations, the database should not be closed in this case, for example because the database is still used at virtual machine shutdown (to store the shutdown process in the database for example).
For those cases, the automatic closing of the database can be disabled in the database URL.
The first connection (the one that is opening the database) needs to set the option in the database URL (it is not possible to change the setting afterwards).
The database URL to disable database closing on exit is:


默认情况下，数据库在最后一个连接关闭时关闭。
但是，如果它从未关闭，则在虚拟机正常退出时，使用关闭挂钩关闭数据库。
在某些情况下，在这种情况下不应该关闭数据库，例如因为数据库在虚拟机关闭时仍在使用（例如将关闭过程存储在数据库中）。
对于这些情况，可以在数据库 URL 中禁用数据库的自动关闭。
第一个连接（打开数据库的连接）需要在数据库 URL 中设置选项（之后无法更改设置）。
在退出时禁用数据库关闭的数据库 URL 是：

---

```
String url = "jdbc:h2:~/test;DB_CLOSE_ON_EXIT=FALSE";
```
