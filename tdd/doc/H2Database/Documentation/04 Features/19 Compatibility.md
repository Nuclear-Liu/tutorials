## Compatibility *兼容性*

All database engines behave a little bit different.
Where possible, H2 supports the ANSI SQL standard, and tries to be compatible to other databases.
There are still a few differences however:


所有数据库引擎的行为都略有不同。
在可能的情况下，H2 支持 ANSI SQL 标准，并尝试与其他数据库兼容。
但是，仍然存在一些差异：

---

In MySQL text columns are case insensitive by default, while in H2 they are case sensitive.
However H2 supports case insensitive columns as well.
To create the tables with case insensitive texts, append `IGNORECASE=TRUE` to the database URL (example: `jdbc:h2:~/test;IGNORECASE=TRUE`).


在 MySQL 中，默认情况下文本列不区分大小写，而在 H2 中它们**区分大小写**。
但是 H2 也支持不区分大小写的列。
要创建不区分大小写文本的表，请将 `IGNORECASE=TRUE` 附加到数据库 URL（例如： `jdbc:h2:~/test;IGNORECASE=TRUE` ）。

---

### Compatibility Modes *兼容模式*

For certain features, this database can emulate the behavior of specific databases.
However, only a small subset of the differences between databases are implemented in this way.
Here is the list of currently supported modes and the differences to the regular mode:


对于某些功能，该数据库可以模拟特定数据库的行为。
但是，只有一小部分数据库之间的差异是通过这种方式实现的。
以下是当前支持的模式列表以及与常规模式的区别：

---

### DB2 Compatibility Mode *DB2 兼容模式*

To use the IBM DB2 mode, use the database URL `jdbc:h2:~/test;MODE=DB2` or the SQL statement `SET MODE DB2`.



要使用 IBM DB2 模式，请使用数据库 URL `jdbc:h2:~test;MODE=DB2` 或 SQL 语句 `SET MODE DB2` 。

---

* For aliased columns, `ResultSetMetaData.getColumnName()` returns the alias name and `getTableName()` returns `null`.
* Concatenating `NULL` with another value results in the other value.
* Support the pseudo-table `SYSIBM.SYSDUMMY1`.
* Timestamps with dash between date and time are supported.
* Datetime value functions return the same value within a command.
* Second and third arguments of `TRANSLATE()` function are swapped.


* 对于别名列， `ResultSetMetaData.getColumnName()` 返回别名， `getTableName()` 返回 `null` 。
* 将 `NULL` 与另一个值连接会产生另一个值。
* 支持伪表 `SYSIBM.SYSDUMMY1` 。
* 支持日期和时间之间带有破折号的时间戳。
* 日期时间值函数在命令中返回相同的值。
* `TRANSLATE()` 函数的第二个和第三个参数交换。

---

### Derby Compatibility Mode *Derby 兼容模式*

To use the Apache Derby mode, use the database URL `jdbc:h2:~/test;MODE=Derby` or the SQL statement `SET MODE Derby`.


要使用 Apache Derby 模式，请使用数据库 URL `jdbc:h2:~test;MODE=Derby` 或 SQL 语句 `SET MODE Derby` 。

---

* For aliased columns, `ResultSetMetaData.getColumnName()` returns the alias name and `getTableName()` returns `null`.
* For unique indexes, `NULL` is distinct. That means only one row with `NULL` in one of the columns is allowed.
* Concatenating `NULL` with another value results in the other value.
* Support the pseudo-table `SYSIBM.SYSDUMMY1`.
* Datetime value functions return the same value within a command.


* 对于别名列， `ResultSetMetaData.getColumnName()` 返回别名， `getTableName()` 返回 `null` 。
* 对于唯一索引， `NULL` 是不同的。这意味着在其中一列中只允许一行带有 `NULL` 。
* 将 `NULL` 与另一个值连接会产生另一个值。
* 支持伪表 `SYSIBM.SYSDUMMY1` 。
* 日期时间值函数在命令中返回相同的值。

---

### HSQLDB Compatibility Mode *HSQLDB 兼容模式*

To use the HSQLDB mode, use the database URL `jdbc:h2:~/test;MODE=HSQLDB` or the SQL statement `SET MODE HSQLDB`.


要使用 HSQLDB 模式，请使用数据库 URL `jdbc:h2:~test;MODE=HSQLDB` 或 SQL 语句 `SET MODE HSQLDB` 。

---

* Text can be concatenated using '+'.
* Datetime value functions return the same value within a command.


* 可以使用 '+' 连接文本。
* 日期时间值函数在命令中返回相同的值。

---

### MS SQL Server Compatibility Mode *MS SQL Server 兼容模式*

To use the MS SQL Server mode, use the database URL `jdbc:h2:~/test;MODE=MSSQLServer` or the SQL statement `SET MODE MSSQLServer`.


要使用 MS SQL Server 模式，请使用数据库 URL `jdbc:h2:~test;MODE=MSSQLServer` 或 SQL 语句 `SET MODE MSSQLServer` 。

---

* For aliased columns, `ResultSetMetaData.getColumnName()` returns the alias name and `getTableName()` returns `null`.
* Identifiers may be quoted using square brackets as in `[Test]`.
* For unique indexes, `NULL` is distinct. That means only one row with `NULL` in one of the columns is allowed.
* Concatenating `NULL` with another value results in the other value.
* Text can be concatenated using '+'.
* Arguments of LOG() function are swapped.
* MONEY data type is treated like NUMERIC(19, 4) data type. SMALLMONEY data type is treated like NUMERIC(10, 4) data type.
* `IDENTITY` can be used for automatic id generation on column level.
* Table hints are discarded. Example: `SELECT * FROM table WITH (NOLOCK)`.
* Datetime value functions return the same value within a command.
* 0x literals are parsed as binary string literals.
* TRUNCATE TABLE restarts next values of generated columns.


* 对于别名列， `ResultSetMetaData.getColumnName()` 返回别名， `getTableName()` 返回 `null` 。
* 标识符可以使用方括号引用，如 `[Test]` 。
* 对于唯一索引， `NULL` 是不同的。这意味着在其中一列中只允许一行带有 `NULL` 。
* 将 `NULL` 与另一个值连接会产生另一个值。
* 可以使用 '+' 连接文本。
* LOG() 函数的参数被交换。
* `MONEY` 数据类型被视为 `NUMERIC(19, 4)` 数据类型。 SMALLMONEY 数据类型被视为 `NUMERIC(10, 4)` 数据类型。
* `IDENTITY` 可用于在列级别自动生成 id。
* 表提示被丢弃。示例： `SELECT FROM table WITH (NOLOCK)` 。
* 日期时间值函数在命令中返回相同的值。
* 0x 文字被解析为二进制字符串文字。
* TRUNCATE TABLE 重新启动生成列的下一个值。

---

### MySQL Compatibility Mode *MySQL 兼容模式*

To use the MySQL mode, use the database URL `jdbc:h2:~/test;MODE=MySQL;DATABASE_TO_LOWER=TRUE`.
Use this mode for compatibility with MariaDB too.
When case-insensitive identifiers are needed append `;CASE_INSENSITIVE_IDENTIFIERS=TRUE` to URL.
Do not change value of DATABASE_TO_LOWER after creation of database.


要使用 MySQL 模式，请使用数据库 URL `jdbc:h2:~test;MODE=MySQL;DATABASE_TO_LOWER=TRUE` 。
也可以使用此模式与 MariaDB 兼容。
当需要不区分大小写的标识符时，将 `;CASE_INSENSITIVE_IDENTIFIERS=TRUE` 附加到 URL。
创建数据库后不要更改 DATABASE_TO_LOWER 的值。

---

* Creating indexes in the `CREATE TABLE` statement is allowed using `INDEX(..)` or `KEY(..)`.
  Example: `create table test(id int primary key, name varchar(255), key idx_name(name));`
* When converting a floating point number to an integer, the fractional digits are not truncated, but the value is rounded.
* Concatenating `NULL` with another value results in the other value.
* ON DUPLICATE KEY UPDATE is supported in INSERT statements, due to this feature VALUES has special non-standard meaning is some contexts.
* INSERT IGNORE is partially supported and may be used to skip rows with duplicate keys if ON DUPLICATE KEY UPDATE is not specified.
* REPLACE INTO is partially supported.
* REGEXP_REPLACE() uses \ for back-references for compatibility with MariaDB.
* Datetime value functions return the same value within a command.
* 0x literals are parsed as binary string literals.
* Unrelated expressions in ORDER BY clause of DISTINCT queries are allowed.
* Some MySQL-specific ALTER TABLE commands are partially supported.
* TRUNCATE TABLE restarts next values of generated columns.


* 允许使用 `INDEX(..)` 或 `KEY(..)` 在 `CREATE TABLE` 语句中创建索引。
  示例： `create table test(id int primary key, name varchar(255), key idx_name(name));`
* 将浮点数转换为整数时，小数位不会被截断，但值会被四舍五入。
* 将 `NULL` 与另一个值连接会产生另一个值。
* INSERT 语句中支持 ON DUPLICATE KEY UPDATE，由于此功能 VALUES 在某些上下文中具有特殊的非标准含义。
* INSERT IGNORE 部分受支持，如果未指定 ON DUPLICATE KEY UPDATE，则可用于跳过具有重复键的行。
* 部分支持 REPLACE INTO。
* REGEXP_REPLACE() 使用 \ 作为反向引用以与 MariaDB 兼容。
* 日期时间值函数在命令中返回相同的值。
* 0x 文字被解析为二进制字符串文字。
* 允许 DISTINCT 查询的 ORDER BY 子句中的无关表达式。
* 部分支持某些特定于 MySQL 的 ALTER TABLE 命令。
* TRUNCATE TABLE 重新启动生成列的下一个值。

---

Text comparison in MySQL is case insensitive by default, while in H2 it is case sensitive (as in most other databases).
H2 does support case insensitive text comparison, but it needs to be set separately, using `SET IGNORECASE TRUE`.
This affects comparison using `=, LIKE, REGEXP`.


默认情况下，MySQL 中的文本比较不区分大小写，而在 H2 中则区分大小写（与大多数其他数据库一样）。
H2确实支持不区分大小写的文本比较，但需要单独设置，使用 `SET IGNORECASE TRUE` 。
这会影响使用 `=, LIKE, REGEXP` 的比较。

---

### Oracle Compatibility Mode *Oracle 兼容模式*

To use the Oracle mode, use the database URL `jdbc:h2:~/test;MODE=Oracle` or the SQL statement `SET MODE Oracle`.


要使用 Oracle 模式，请使用数据库 URL `jdbc:h2:~test;MODE=Oracle` 或 SQL 语句 `SET MODE Oracle`。

---

* For aliased columns, `ResultSetMetaData.getColumnName()` returns the alias name and `getTableName()` returns `null`.
* When using unique indexes, multiple rows with `NULL` in all columns are allowed, however it is not allowed to have multiple rows with the same values otherwise.
* Concatenating `NULL` with another value results in the other value.
* Empty strings are treated like `NULL` values.
* REGEXP_REPLACE() uses \ for back-references.
* RAWTOHEX() converts character strings to hexadecimal representation of their UTF-8 encoding.
* HEXTORAW() decodes a hexadecimal character string to a binary string.
* DATE data type is treated like TIMESTAMP(0) data type.
* Datetime value functions return the same value within a command.
* ALTER TABLE MODIFY COLUMN command is partially supported.
* SEQUENCE.NEXTVAL and SEQUENCE.CURRVAL return values with DECIMAL/NUMERIC data type.


* 对于别名列， `ResultSetMetaData.getColumnName()` 返回别名， `getTableName()` 返回 `null` 。
* 使用唯一索引时，允许在所有列中包含“NULL”的多行，否则不允许多行具有相同的值。
* 将 `NULL` 与另一个值连接会产生另一个值。
* 空字符串被视为 `NULL` 值。
* REGEXP_REPLACE() 使用 \ 进行反向引用。
* RAWTOHEX() 将字符串转换为其 UTF-8 编码的十六进制表示。
* HEXTORAW() 将十六进制字符串解码为二进制字符串。
* DATE 数据类型被视为 TIMESTAMP(0) 数据类型。
* 日期时间值函数在命令中返回相同的值。
* 部分支持 ALTER TABLE MODIFY COLUMN 命令。
* SEQUENCE.NEXTVAL 和 SEQUENCE.CURRVAL 返回 DECIMALNUMERIC 数据类型的值。

---

### PostgreSQL Compatibility Mode *PostgreSQL 兼容模式*

To use the PostgreSQL mode, use the database URL `jdbc:h2:~/test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE`. 
Do not change value of DATABASE_TO_LOWER after creation of database.


要使用 PostgreSQL 模式，请使用数据库 URL `jdbc:h2:~test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE` 。
创建数据库后不要更改 DATABASE_TO_LOWER 的值。

---

* For aliased columns, `ResultSetMetaData.getColumnName()` returns the alias name and `getTableName()` returns `null`.
* When converting a floating point number to an integer, the fractional digits are not be truncated, but the value is rounded.
* The system columns `CTID` and `OID` are supported.
* LOG(x) is base 10 in this mode.
* REGEXP_REPLACE():
    * uses \ for back-references;
    * does not throw an exception when the `flagsString` parameter contains a 'g';
    * replaces only the first matched substring in the absence of the 'g' flag in the `flagsString` parameter.
* ON CONFLICT DO NOTHING is supported in INSERT statements.
* Fixed-width strings are padded with spaces.
* MONEY data type is treated like NUMERIC(19, 2) data type.
* Datetime value functions return the same value within a transaction.
* ARRAY_SLICE() out of bounds parameters are silently corrected.
* EXTRACT function with DOW field returns (0-6), Sunday is 0.


* 对于别名列， `ResultSetMetaData.getColumnName()` 返回别名， `getTableName()` 返回 `null` 。
* 将浮点数转换为整数时，小数位不会被截断，但值会四舍五入。
* 支持系统列 `CTID` 和 `OID` 。
* 在此模式下，LOG(x) 以 10 为底。
* REGEXP_REPLACE() ：
  * 使用 \ 作为反向引用；
  * 当 `flagsString` 参数包含一个 'g' 时不会抛出异常；
  * 如果 `flagsString` 参数中没有 'g' 标志，则仅替换第一个匹配的子字符串。
* INSERT 语句支持 ON CONFLICT DO NOTHING 。
* 固定宽度的字符串用空格填充。
* MONEY 数据类型被视为 NUMERIC(19, 2) 数据类型。
* 日期时间值函数在事务中返回相同的值。
* ARRAY_SLICE() 越界参数被静默纠正。
* 带有 DOW 字段的 EXTRACT 函数返回 (0-6)，星期日为 0。

---

### Ignite Compatibility Mode *Ignite 兼容模式*

To use the Ignite mode, use the database URL `jdbc:h2:~/test;MODE=Ignite` or the SQL statement `SET MODE Ignite`.


要使用 Ignite 模式，请使用数据库 URL `jdbc:h2:~test;MODE=Ignite` 或 SQL 语句 `SET MODE Ignite` 。

---

* Creating indexes in the `CREATE TABLE` statement is allowed using `INDEX(..)` or `KEY(..)`. 
  Example: `create table test(id int primary key, name varchar(255), key idx_name(name));`
* AFFINITY KEY and SHARD KEY keywords may be used in index definition.
* Datetime value functions return the same value within a transaction. 


* 允许使用 `INDEX(..)` 或 `KEY(..)` 在 `CREATE TABLE` 语句中创建索引。
  示例： `create table test(id int primary key, name varchar(255), key idx_name(name));`
* AFFINITY KEY 和 SHARD KEY 关键字可用于索引定义。
* 日期时间值函数在事务中返回相同的值。

---
