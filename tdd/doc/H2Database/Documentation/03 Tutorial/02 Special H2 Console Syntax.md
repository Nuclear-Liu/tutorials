## Special H2 Console Syntax *特殊的 H2 控制台语法*

The H2 Console supports a few built-in commands.
Those are interpreted within the H2 Console, so they work with any database.
Built-in commands need to be at the beginning of a statement (before any remarks), otherwise they are not parsed correctly.
If in doubt, add `;` before the command.


H2 控制台支持一些内置命令。
这些在 H2 控制台中进行解释，因此它们适用于任何数据库。
内置命令需要在语句的开头（在任何注释之前），否则它们将无法正确解析。
如果有疑问，请在**命令前添加`;`** 。

----

| Command(s) | Description |
| ---- | ---- |
| `@autocommit_true;` `@autocommit_false;`  | Enable or disable autocommit.  |
| `@columns null null TEST;` `@index_info null null TEST;` `@tables;` `@tables null null TEST;` | Call the corresponding `DatabaseMetaData.get` method. Patterns are case sensitive (usually identifiers are uppercase). For information about the parameters, see the Javadoc documentation. Missing parameters at the end of the line are set to null. The complete list of metadata commands is: `@attributes`, `@best_row_identifier`, `@catalogs`, `@columns`, `@column_privileges`, `@cross_references`, `@exported_keys`, `@imported_keys`, `@index_info`, `@primary_keys`, `@procedures`, `@procedure_columns`, `@schemas`, `@super_tables`, `@super_types`, `@tables`, `@table_privileges`, `@table_types`, `@type_info`, `@udts`, `@version_columns`  |
| `@edit select * from test;`  | Use an updatable result set.  |
| `@generated insert into test() values();` `@generated(1) insert into test() values();` `@generated(ID, "TIMESTAMP") insert into test() values();`  | Show the result of `Statement.getGeneratedKeys()`. Names or one-based indexes of required columns can be optionally specified.  |
| `@history;`  | List the command history.  |
| `@info;`  | Display the result of various `Connection` and `DatabaseMetaData` methods. |
| `@list select * from test;`  |  |
| `@loop 1000 select ?, ?/*rnd*/;` `@loop 1000 @statement select ?;`  | Run the statement this many times. Parameters (`?`) are set using a loop from 0 up to x - 1. Random values are used for each `?/*rnd*/`. A Statement object is used instead of a PreparedStatement if `@statement` is used. Result sets are read until `ResultSet.next()` returns `false`. Timing information is printed.  |
| `@maxrows 20;`  | Set the maximum number of rows to display.  |
| `@memory;`  | Show the used and free memory. This will call `System.gc()`.  |
| `@meta select 1;`  | List the `ResultSetMetaData` after running the query.  |
| `@parameter_meta select ?;`  | Show the result of the `PreparedStatement.getParameterMetaData()` calls. The statement is not executed.  |
| `@prof_start;` `call hash('SHA256', '', 1000000);` `@prof_stop;`  | Start/stop the built-in profiling tool. The top 3 stack traces of the statement(s) between start and stop are listed (if there are 3).  |
| `@prof_start;` `@sleep 10;` `@prof_stop;`  | Sleep for a number of seconds. Used to profile a long running query or operation that is running in another session (but in the same process).  |
| `@transaction_isolation;` `@transaction_isolation 2;`  | Display (without parameters) or change (with parameters 1, 2, 4, 8) the transaction isolation level.  |


| Command(s) | 描述 |
| ---- | ---- |
| `@autocommit_true;` `@autocommit_false;` | 启用或禁用自动提交。 |
| `@columns null null TEST;` `@index_info null null TEST;` `@tables; @tables null null TEST;` | 调用相应的`DatabaseMetaData.get` 方法。 模式区分大小写（通常标识符是大写的）。 有关参数的信息，请参阅 Javadoc 文档。 行尾缺少的参数设置为空。 元数据命令的完整列表是： `@attributes`, `@best_row_identifier`, `@catalogs`, `@columns`, `@column_privileges`, `@cross_references`, `@exported_keys`, `@imported_keys`, `@index_info`, `@primary_keys`, `@procedures`, `@procedure_columns`, `@schemas`, `@super_tables`, `@super_types`, `@tables`, `@table_privileges`, `@table_types`, `@type_info`, `@udts`, `@version_columns`  |
| `@edit select * from test;` | 使用可更新的结果集。 |
| `@generated insert into test() values();` `@generated(1) insert into test() values();` `@generated(ID, "TIMESTAMP") insert into test() values();` | 显示 `Statement.getGeneratedKeys()` 的结果。 可以选择指定所需列的名称或从一开始的索引。 |
| `@history;`  | 列出命令历史。 |
| `@info;`  | 显示各种 `Connection` 和 `DatabaseMetaData` 方法的结果。 |
| `@list select * from test;`  |  |
| `@loop 1000 select ?, ?/*rnd*/;` `@loop 1000 @statement select ?;`  | 多次运行该语句。 参数 (`?`) 使用从 0 到 x - 1 的循环设置。 每个`?rnd` 使用随机值。 如果使用 `@statement`，则使用 Statement 对象而不是 PreparedStatement。 读取结果集，直到 `ResultSet.next()` 返回 `false`。 打印定时信息。 |
| `@maxrows 20;`  | 设置要显示的最大行数。 |
| `@memory;`  | 显示已用和空闲内存。这将调用 `System.gc()` 。 |
| `@meta select 1;`  | 运行查询后列出 `ResultSetMetaData` 。 |
| `@parameter_meta select ?;`  | 显示 `PreparedStatement.getParameterMetaData()` 调用的结果。 不执行该语句。 |
| `@prof_start;` `call hash('SHA256', '', 1000000);` `@prof_stop;`  | Start/stop 内置的分析工具。 列出了 start 和 stop 之间语句的前 3 个堆栈跟踪（如果有 3 个）。 |
| `@prof_start;` `@sleep 10;` `@prof_stop;`  | 睡眠几秒钟。 用于分析在另一个会话中运行的长时间运行的查询或操作（但在同一进程中）。 |
| `@transaction_isolation;` `@transaction_isolation 2;`  | 显示（不带参数）或更改（带参数 1、2、4、8）事务隔离级别。 |

----
