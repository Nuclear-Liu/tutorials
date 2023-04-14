## Compacting a Database *压缩数据库*

Empty space in the database file re-used automatically.
When closing the database, the database is automatically compacted for up to 200 milliseconds by default.
To compact more, use the SQL statement SHUTDOWN COMPACT.
However re-creating the database may further reduce the database size because this will re-build the indexes.
Here is a sample function to do this:


数据库文件中的空白空间自动重新使用。
关闭数据库时，数据库默认自动压缩最多 200 毫秒。
要压缩更多，请使用 SQL 语句 SHUTDOWN COMPACT。
但是，重新创建数据库可能会进一步减小数据库大小，因为这将重新构建索引。
这是执行此操作的示例函数：

---

```
public static void compact(String dir, String dbName,
        String user, String password) throws Exception {
    String url = "jdbc:h2:" + dir + "/" + dbName;
    String file = "data/test.sql";
    Script.execute(url, user, password, file);
    DeleteDbFiles.execute(dir, dbName, true);
    RunScript.execute(url, user, password, file, null, false);
}
```

See also the sample application `org.h2.samples.Compact`.
The commands `SCRIPT / RUNSCRIPT` can be used as well to create a backup of a database and re-build the database from the script. 


另请参阅示例应用程序 `org.h2.samples.Compact` 。
命令 `SCRIPT RUNSCRIPT` 也可用于创建数据库备份并从脚本重建数据库。

---
