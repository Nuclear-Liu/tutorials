## Command Line Tools *命令行工具*

This database comes with a number of command line tools.
To get more information about a tool, start it with the parameter '-?', for example:


该数据库带有许多命令行工具。
要获取有关工具的更多信息，请以参数“-?”开头，例如：

----

```shell
java -cp h2*.jar org.h2.tools.Backup -?
```

The command line tools are:


命令行工具有：

---

* `Backup` creates a backup of a database.
* `ChangeFileEncryption` allows changing the file encryption password or algorithm of a database.
* `Console` starts the browser based H2 Console.
* `ConvertTraceFile` converts a .trace.db file to a Java application and SQL script.
* `CreateCluster` creates a cluster from a standalone database.
* `DeleteDbFiles` deletes all files belonging to a database.
* `Recover` helps recovering a corrupted database.
* `Restore` restores a backup of a database.
* `RunScript` runs a SQL script against a database.
* `Script` allows converting a database to a SQL script for backup or migration.
* `Server` is used in the server mode to start a H2 server.
* `Shell` is a command line database tool.


* `Backup` 创建数据库的备份。
* `ChangeFileEncryption` 允许更改数据库的文件加密密码或算法。
*`Console` 启动基于浏览器的 H2 控制台。
* `ConvertTraceFile` 将 .trace.db 文件转换为 Java 应用程序和 SQL 脚本。
* `CreateCluster` 从一个独立的数据库创建一个集群。
* `DeleteDbFiles` 删除属于数据库的所有文件。
* `Recover` 有助于恢复损坏的数据库。
* `Restore` 恢复数据库的备份。
* `RunScript` 针对数据库运行 SQL 脚本。
* `Script` 允许将数据库转换为 SQL 脚本以进行备份或迁移。
* `Server` 在服务器模式下用于启动 H2 服务器。
* `Shell` 是一个命令行数据库工具。

----

The tools can also be called from an application by calling the main or another public method.
For details, see the Javadoc documentation.


也可以通过调用 main 或其他公共方法从应用程序调用这些工具。
有关详细信息，请参阅 Javadoc 文档。

----
