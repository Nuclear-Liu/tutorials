## Upgrade, Backup, and Restore *升级，备份和还原*

### Database Upgrade *数据库升级*

The recommended way to upgrade from one version of the database engine to the next version is to create a backup of the database (in the form of a SQL script) using the old engine, and then execute the SQL script using the new engine.

----

从一个版本的数据库引擎升级到下一个版本的推荐方法是使用旧引擎创建数据库的备份（以 SQL 脚本的形式），然后使用新引擎执行 SQL 脚本。

----

### Backup using the Script Tool *使用脚本工具进行备份*

The recommended way to backup a database is to create a compressed SQL script file.
This will result in a small, human readable, and database version independent backup.
Creating the script will also verify the checksums of the database file.
The `Script` tool is ran as follows:

----

备份数据库的推荐方法是创建一个压缩的 SQL 脚本文件。
这将产生一个小的、人类可读的和数据库版本独立的备份。
创建脚本还将验证数据库文件的校验和。
`Script` 工具运行如下：

----

```shell
java org.h2.tools.Script -url jdbc:h2:~/test -user sa -script test.zip -options compression zip
```

It is also possible to use the SQL command `SCRIPT` to create the backup of the database.
For more information about the options, see the SQL command `SCRIPT`.
The backup can be done remotely, however the file will be created on the server side.
The built in FTP server could be used to retrieve the file from the server.


也可以使用 SQL 命令 `SCRIPT` 来创建数据库的备份。
有关选项的更多信息，请参阅 SQL 命令 `SCRIPT` 。
备份可以远程完成，但文件将在服务器端创建。
内置的 FTP 服务器可用于从服务器检索文件。

----

### Restore from a Script *从脚本还原*

To restore a database from a SQL script file, you can use the RunScript tool:


要从 SQL 脚本文件恢复数据库，您可以使用 RunScript 工具：

----

```shell
java org.h2.tools.RunScript -url jdbc:h2:~/test -user sa -script test.zip -options compression zip
```

For more information about the options, see the SQL command `RUNSCRIPT`.
The restore can be done remotely, however the file needs to be on the server side.
The built in FTP server could be used to copy the file to the server.
It is also possible to use the SQL command `RUNSCRIPT` to execute a SQL script.
SQL script files may contain references to other script files, in the form of `RUNSCRIPT` commands.
However, when using the server mode, the references script files need to be available on the server side.


有关选项的更多信息，请参阅 SQL 命令 `RUNSCRIPT` 。
恢复可以远程完成，但是文件需要在服务器端。
内置的 FTP 服务器可用于将文件复制到服务器。
也可以使用 SQL 命令 `RUNSCRIPT` 来执行 SQL 脚本。
但是，在使用服务器模式时，需要在服务器端提供引用脚本文件。

----

### Online Backup *在线备份*

The `BACKUP` SQL statement and the `Backup` tool both create a zip file with the database file.
However, the contents of this file are not human readable.


`BACKUP` SQL 语句和 `Backup` 工具都使用数据库文件创建一个 zip 文件。
但是，该文件的内容不是人类可读的。

----

The resulting backup is transactionally consistent, meaning the consistency and atomicity rules apply.


生成的备份在事务上是一致的，这意味着一致性和原子性规则适用。

----

```shell
BACKUP TO 'backup.zip'
```

The `Backup` tool (`org.h2.tools.Backup`) can not be used to create a online backup; the database must not be in use while running this program.


`Backup` 工具（`org.h2.tools.Backup`）不能用于创建在线备份；运行此程序时不得使用数据库。

----

Creating a backup by copying the database files while the database is running is not supported, except if the file systems support creating snapshots.
With other file systems, it can't be guaranteed that the data is copied in the right order.


不支持通过在数据库运行时复制数据库文件来创建备份，除非文件系统支持创建快照。
对于其他文件系统，无法保证以正确的顺序复制数据。

----
