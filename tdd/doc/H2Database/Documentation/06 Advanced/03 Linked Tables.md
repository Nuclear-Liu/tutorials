## Linked Tables *链接表*

This database supports linked tables, which means tables that don't exist in the current database but are just links to another database.
To create such a link, use the `CREATE LINKED TABLE` statement:


该数据库支持链接表，这意味着当前数据库中不存在的表只是到另一个数据库的链接。
要创建这样的链接，请使用 `CREATE LINKED TABLE` 语句：

---

```sql
CREATE LINKED TABLE LINK('org.postgresql.Driver', 'jdbc:postgresql:test', 'sa', 'sa', 'TEST');
```

You can then access the table in the usual way.
Whenever the linked table is accessed, the database issues specific queries over JDBC.
Using the example above, if you issue the query `SELECT * FROM LINK WHERE ID=1`, then the following query is run against the PostgreSQL database: `SELECT * FROM TEST WHERE ID=?`.
The same happens for insert and update statements.
Only simple statements are executed against the target database, that means no joins (queries that contain joins are converted to simple queries).
Prepared statements are used where possible.


然后，您可以以通常的方式访问该表。
每当访问链接表时，数据库都会通过 JDBC 发出特定查询。
使用上面的示例，如果您发出查询 `SELECT FROM LINK WHERE ID=1` ，那么将针对 PostgreSQL 数据库运行以下查询： `SELECT FROM TEST WHERE ID=?` 。
插入和更新语句也是如此。
只对目标数据库执行简单语句，这意味着没有连接（包含连接的查询转换为简单查询）。
在可能的情况下使用准备好的语句。

---

To view the statements that are executed against the target table, set the trace level to 3.


要查看针对目标表执行的语句，请将跟踪级别设置为 3。

---

If multiple linked tables point to the same database (using the same database URL), the connection is shared.
To disable this, set the system property `h2.shareLinkedConnections=false`.


如果多个链接表指向同一个数据库（使用同一个数据库 URL），则连接是共享的。
要禁用此功能，请设置系统属性 `h2.shareLinkedConnections=false` 。

---

The statement [CREATE LINKED TABLE]() supports an optional schema name parameter.


语句 [CREATE LINKED TABLE]() 支持可选的模式名称参数。

---

The following are not supported because they may result in a deadlock: 
creating a linked table to the same database, and creating a linked table to another database using the server mode if the other database is open in the same server (use the embedded mode instead).


不支持以下内容，因为它们可能导致死锁：
创建到同一个数据库的链接表，如果另一个数据库在同一台服务器上打开，则使用服务器模式创建到另一个数据库的链接表（改用嵌入模式）。

---

Data types that are not supported in H2 are also not supported for linked tables, for example unsigned data types if the value is outside the range of the signed type.
In such cases, the columns needs to be cast to a supported type.


链接表也不支持 H2 中不支持的数据类型，例如，如果值超出有符号类型的范围，则为无符号数据类型。
在这种情况下，需要将列转换为支持的类型。

---
