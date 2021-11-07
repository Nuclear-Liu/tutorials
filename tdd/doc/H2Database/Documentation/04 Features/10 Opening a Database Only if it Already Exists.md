## Opening a Database Only if it Already Exists *仅在已存在的情况下打开数据库*

By default, when an application calls `DriverManager.getConnection(url, ...)` with [embedded]() URL and the database specified in the URL does not yet exist, a new (empty) database is created.
In some situations, it is better to restrict creating new databases, and only allow to open existing databases.
To do this, add `;IFEXISTS=TRUE` to the database URL.
In this case, if the database does not already exist, an exception is thrown when trying to connect.
The connection only succeeds when the database already exists. 
The complete URL may look like this:


默认情况下，当应用程序使用 [embedded]() URL 调用 `DriverManager.getConnection(url, ...)` 并且 URL 中指定的数据库尚不存在时，会创建一个新的（空）数据库。
在某些情况下，最好限制创建新数据库，只允许打开现有数据库。
为此，请将 `;IFEXISTS=TRUE` 添加到数据库 URL。
在这种情况下，如果数据库尚不存在，则尝试连接时会抛出异常。
在这种情况下，如果数据库尚不存在，则尝试连接时会抛出异常。
只有当数据库已经存在时，连接才会成功。
完整的 URL 可能如下所示：

---

```
String url = "jdbc:h2:/data/sample;IFEXISTS=TRUE";
```
