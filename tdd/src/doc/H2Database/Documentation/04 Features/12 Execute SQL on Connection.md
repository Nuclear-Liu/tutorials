## Execute SQL on Connection *在连接上执行 SQL*

Sometimes, particularly for in-memory databases, it is useful to be able to execute DDL or DML commands automatically when a client connects to a database.
This functionality is enabled via the INIT property.
Note that multiple commands may be passed to INIT, but the semicolon delimiter must be escaped, as in the example below.


有时，特别是对于内存数据库，能够在客户端连接到数据库时自动执行 DDL 或 DML 命令很有用。
此功能通过 INIT 属性启用。
请注意，可以将多个命令传递给 INIT，但必须对分号分隔符进行转义，如下例所示。

---

```
String url = "jdbc:h2:mem:test;INIT=runscript from '~/create.sql'\\;runscript from '~/init.sql'";
```

Please note the double backslash is only required in a Java or properties file.
In a GUI, or in an XML file, only one backslash is required:


请注意双反斜杠仅在 Java 或属性文件中是必需的。
在 GUI 或 XML 文件中，只需要一个反斜杠：

---

```xml
<property name="url" value=
"jdbc:h2:mem:test;INIT=create schema if not exists test\;runscript from '~/sql/init.sql'"
/>
```

Backslashes within the init script (for example within a runscript statement, to specify the folder names in Windows) need to be escaped as well (using a second backslash).
It might be simpler to avoid backslashes in folder names for this reason; use forward slashes instead. 


init 脚本中的反斜杠（例如在 runscript 语句中，用于指定 Windows 中的文件夹名称）也需要转义（使用第二个反斜杠）。
出于这个原因，避免文件夹名称中的反斜杠可能更简单；改用正斜杠。 

---
