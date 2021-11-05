## The Shell Tool *Shell 工具*

The Shell tool is a simple interactive command line tool. 
To start it, type:


Shell 工具是一个简单的交互式命令行工具。
要启动它，请键入：

----

```shell
java -cp h2*.jar org.h2.tools.Shell
```

You will be asked for a database URL, JDBC driver, user name, and password. 
The connection setting can also be set as command line parameters.
After connecting, you will get the list of options.
The built-in commands don't need to end with a semicolon, but SQL statements are only executed if the line ends with a semicolon `;`.
This allows to enter multi-line statements:


系统会要求您提供数据库 URL、JDBC 驱动程序、用户名和密码。
连接设置也可以设置为命令行参数。
连接后，您将获得选项列表。
内置命令不需要以分号结尾，但只有当行以分号 `;` 结尾时，SQL 语句才会执行。
这允许输入多行语句：

----

```shell
sql> select * from test
...> where id = 0;
```

By default, results are printed as a table. 
For results with many column, consider using the list mode:


默认情况下，结果打印为表格。
对于多列的结果，请考虑使用列表模式：

----

```shell
sql> list
Result list mode is now on
sql> select * from test;
ID  : 1
NAME: Hello

ID  : 2
NAME: World
(2 rows, 0 ms)
```
