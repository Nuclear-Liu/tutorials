## Protection against SQL Injection *防止 SQL 注入*

### What is SQL Injection *什么是 SQL 注入*

This database engine provides a solution for the security vulnerability known as 'SQL Injection'.
Here is a short description of what SQL injection means.
Some applications build SQL statements with embedded user input such as:


该数据库引擎为称为“SQL 注入”的安全漏洞提供了解决方案。
以下是 SQL 注入含义的简短说明。
一些应用程序使用嵌入式用户输入构建 SQL 语句，例如：

---

```
String sql = "SELECT * FROM USERS WHERE PASSWORD='"+pwd+"'";
ResultSet rs = conn.createStatement().executeQuery(sql);
```

If this mechanism is used anywhere in the application, and user input is not correctly filtered or encoded, it is possible for a user to inject SQL functionality or statements by using specially built input such as (in this example) this password: `' OR ''='`.
In this case the statement becomes:


如果此机制在应用程序中的任何地方使用，并且用户输入未正确过滤或编码，则用户可能会通过使用专门构建的输入（例如（在本例中）此密码）来注入 SQL 功能或语句：`' OR ''='`。
在这种情况下，语句变为：

---

```sql
SELECT * FROM USERS WHERE PASSWORD='' OR ''='';
```

Which is always true no matter what the password stored in the database is.
For more information about SQL Injection, see [Glossary and Links]().


无论存储在数据库中的密码是什么，这总是正确的。
有关 SQL 注入的更多信息，请参阅 [Glossary and Links]() 。

---

### Disabling Literals *禁用文字*

SQL Injection is not possible if user input is not directly embedded in SQL statements.
A simple solution for the problem above is to use a prepared statement:


如果用户输入没有直接嵌入到 SQL 语句中，则 SQL 注入是不可能的。
上述问题的一个简单解决方案是使用准备好的语句：

---

```
String sql = "SELECT * FROM USERS WHERE PASSWORD=?";
PreparedStatement prep = conn.prepareStatement(sql);
prep.setString(1, pwd);
ResultSet rs = prep.executeQuery();
```

This database provides a way to enforce usage of parameters when passing user input to the database.
This is done by disabling embedded literals in SQL statements.
To do this, execute the statement:


该数据库提供了一种在将用户输入传递给数据库时强制使用参数的方法。
这是通过在 SQL 语句中禁用嵌入文字来完成的。
为此，请执行以下语句：

---

```sql
SET ALLOW_LITERALS NONE;
```

Afterwards, SQL statements with text and number literals are not allowed any more.
That means, SQL statement of the form WHERE NAME='abc' or WHERE CustomerId=10 will fail. It is still possible to use prepared statements and parameters as described above.
Also, it is still possible to generate SQL statements dynamically, and use the Statement API, as long as the SQL statements do not include literals.
There is also a second mode where number literals are allowed: SET ALLOW_LITERALS NUMBERS. To allow all literals, execute SET ALLOW_LITERALS ALL (this is the default setting).
Literals can only be enabled or disabled by an administrator.


之后，不再允许带有文本和数字文字的 SQL 语句。
这意味着，WHERE NAME='abc' 或 WHERE CustomerId=10 形式的 SQL 语句将失败。仍然可以使用上述准备好的语句和参数。
此外，仍然可以动态生成 SQL 语句，并使用 Statement API，只要 SQL 语句不包含文字即可。
还有第二种模式允许数字文字：SET ALLOW_LITERALS NUMBERS。要允许所有文字，请执行 SET ALLOW_LITERALS ALL（这是默认设置）。
文字只能由管理员启用或禁用。

---

### Using Constants *使用常量*

Disabling literals also means disabling hard-coded 'constant' literals.
This database supports defining constants using the `CREATE CONSTANT` command.
Constants can be defined only when literals are enabled, but used even when literals are disabled.
To avoid name clashes with column names, constants can be defined in other schemas:


禁用文字也意味着禁用硬编码的“常量”文字。
该数据库支持使用 `CREATE CONSTANT` 命令定义常量。
常量只能在启用文字时定义，但即使在禁用文字时也可以使用。
为了避免名称与列名冲突，可以在其他模式中定义常量：

---

```sql
CREATE SCHEMA CONST AUTHORIZATION SA;
CREATE CONSTANT CONST.ACTIVE VALUE 'Active';
CREATE CONSTANT CONST.INACTIVE VALUE 'Inactive';
SELECT * FROM USERS WHERE TYPE=CONST.ACTIVE;
```

Even when literals are enabled, it is better to use constants instead of hard-coded number or text literals in queries or views.
With constants, typos are found at compile time, the source code is easier to understand and change.


即使启用了文字，最好在查询或视图中使用常量而不是硬编码的数字或文本文字。
使用常量，在编译时发现错别字，源代码更容易理解和更改。

---

### Using the ZERO() Function *使用 ZERO() 函数*

It is not required to create a constant for the number 0 as there is already a built-in function `ZERO()`:


不需要为数字 0 创建一个常量，因为已经有一个内置函数“ZERO()”：

---

```sql
SELECT * FROM USERS WHERE LENGTH(PASSWORD)=ZERO();
```
