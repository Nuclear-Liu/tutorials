## Using the Recover Tool *使用恢复工具*

The `Recover` tool can be used to extract the contents of a database file, even if the database is corrupted.
It also extracts the content of the transaction log and large objects (CLOB or BLOB).
To run the tool, type on the command line:


`Recover` 工具可用于提取数据库文件的内容，即使数据库已损坏。
它还提取事务日志和大对象（CLOB 或 BLOB）的内容。
要运行该工具，请在命令行中键入：

---

```shell
java -cp h2*.jar org.h2.tools.Recover
```

For each database in the current directory, a text file will be created.
This file contains raw insert statements (for the data) and data definition (DDL) statements to recreate the schema of the database.
This file can be executed using the `RunScript` tool or a `RUNSCRIPT FROM` SQL statement.
The script includes at least one `CREATE USER` statement.
If you run the script against a database that was created with the same user, or if there are conflicting users, running the script will fail.
Consider running the script against a database that was created with a user name that is not in the script.


对于当前目录中的每个数据库，将创建一个文本文件。
此文件包含原始插入语句（用于数据）和数据定义 (DDL) 语句以重新创建数据库的架构。
可以使用 `RunScript` 工具或 `RUNSCRIPT FROM` SQL 语句执行该文件。
该脚本至少包含一个 `CREATE USER` 语句。
如果针对使用同一用户创建的数据库运行脚本，或者存在冲突的用户，则运行脚本将失败。
考虑针对使用不在脚本中的用户名创建的数据库运行脚本。

---

The `Recover` tool creates a SQL script from database file.
It also processes the transaction log.


`Recover` 工具从数据库文件创建一个 SQL 脚本。
它还处理事务日志。

---

To verify the database can recover at any time, append `;RECOVER_TEST=64` to the database URL in your test environment.
This will simulate an application crash after each 64 writes to the database file.
A log file named `databaseName.h2.db.log` is created that lists the operations.
The recovery is tested using an in-memory file system, that means it may require a larger heap setting.


要验证数据库可以随时恢复，请将 `;RECOVER_TEST=64` 附加到测试环境中的数据库 URL。
这将在每 64 次写入数据库文件后模拟应用程序崩溃。
创建了一个名为 `databaseName.h2.db.log` 的日志文件，其中列出了操作。
使用内存文件系统测试恢复，这意味着它可能需要更大的堆设置。

---
