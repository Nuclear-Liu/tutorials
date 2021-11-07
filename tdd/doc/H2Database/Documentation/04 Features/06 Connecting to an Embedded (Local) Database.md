## Connecting to an Embedded (Local) Database *连接到嵌入式（本地）数据库*

The database URL for connecting to a local database is `jdbc:h2:[file:][<path>]<databaseName>`.
The prefix `file`: is optional. If no or only a relative path is used, then the current working directory is used as a starting point.
The case sensitivity of the path and database name depend on the operating system, however it is recommended to use lowercase letters only.
The database name must be at least three characters long (a limitation of `File.createTempFile`).
The database name must not contain a semicolon.
To point to the user home directory, use `~/`, as in: `jdbc:h2:~/test`. 


连接到本地数据库的数据库 URL 是 `jdbc:h2:[file:][<path>]<databaseName>` 。
前缀 `file` : 是可选的。如果不使用或仅使用相对路径，则使用当前工作目录作为起点。
路径和数据库名称是否区分大小写取决于操作系统，但建议仅使用小写字母。
数据库名称的长度必须至少为三个字符（ `File.createTempFile` 的限制）。
数据库名称不得包含分号。
要指向用户主目录，请使用 `~` ，如： `jdbc:h2:~test` 。

---
