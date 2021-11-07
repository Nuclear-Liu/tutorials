## Using Other Logging APIs *使用其他日志 API*

By default, this database uses its own native 'trace' facility.
This facility is called 'trace' and not 'log' within this database to avoid confusion with the transaction log.
Trace messages can be written to both file and `System.out`.
In most cases, this is sufficient, however sometimes it is better to use the same facility as the application, for example Log4j.
To do that, this database support SLF4J.


默认情况下，这个数据库使用它自己的原生“trace”工具。
此工具在此数据库中称为 'trace' 而不是 'log' ，以避免与事务日志混淆。
跟踪消息可以写入文件和 `System.out` 。
在大多数情况下，这已经足够了，但有时最好使用与应用程序相同的工具，例如 Log4j。
为此，该数据库支持 SLF4J。

---

`SLF4J` is a simple facade for various logging APIs and allows to plug in the desired implementation at deployment time.
SLF4J supports implementations such as Logback, Log4j, Jakarta Commons Logging (JCL), Java logging, x4juli, and Simple Log.


`SLF4J` 是各种日志 API 的简单外观，并允许在部署时插入所需的实现。
SLF4J 支持 Logback、Log4j、Jakarta Commons Logging (JCL)、Java logging、x4juli 和 Simple Log 等实现。

---

To enable SLF4J, set the file trace level to 4 in the database URL:


要启用 SLF4J，请将数据库 URL 中的文件跟踪级别设置为 4：

---

`jdbc:h2:~/test;TRACE_LEVEL_FILE=4`

Changing the log mechanism is not possible after the database is open, that means executing the SQL statement `SET TRACE_LEVEL_FILE 4` when the database is already open will not have the desired effect.
To use SLF4J, all required jar files need to be in the classpath.
The logger name is `h2database`.
If it does not work, check the file `<database>.trace.db` for error messages. 


数据库打开后无法更改日志机制，这意味着在数据库已打开时执行 SQL 语句“SET TRACE_LEVEL_FILE 4”将不会达到预期的效果。
要使用 SLF4J，所有必需的 jar 文件都需要在类路径中。
日志记录器名称是“h2database”。
如果它不起作用，请检查文件 `<database>.trace.db` 以获取错误消息。

---
