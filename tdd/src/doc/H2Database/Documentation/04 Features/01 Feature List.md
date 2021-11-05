## Feature List *特性列表*

### Main Features *主要特性*

* Very fast database engine
* Open source
* Written in Java
* Supports standard SQL, JDBC API
* Embedded and Server mode, Clustering support
* Strong security features
* The PostgreSQL ODBC driver can be used
* Multi version concurrency
 

* 非常快的数据库引擎
* 开源
* 用Java编写
* 支持标准 SQL 、JDBC API
* 嵌入式和服务器模式，集群支持
* 强大的安全功能
* 可以使用 PostgreSQL ODBC 驱动程序
* 多版本并发

---

### Additional Features *附加的特性*

* Disk based or in-memory databases and tables, read-only database support, temporary tables
* Transaction support (read committed), 2-phase-commit
* Multiple connections, table level locking
* Cost based optimizer, using a genetic algorithm for complex queries, zero-administration
* Scrollable and updatable result set support, large result set, external result sorting, functions can return a result set
* Encrypted database (AES), SHA-256 password encryption, encryption functions, SSL


* 基于磁盘或内存中的数据库和表、只读数据库支持、临时表
* 事务支持（读提交），两阶段提交
* 多连接，表级锁定
* 基于成本的优化器，使用遗传算法进行复杂查询，零管理
* 可滚动和可更新的结果集支持，大结果集，外部结果排序，函数可以返回一个结果集
* 加密数据库 (AES)、SHA-256 密码加密、加密功能、SSL

---

### SQL Support *SQL 支持*

* Support for multiple schemas, information schema
* Referential integrity / foreign key constraints with cascade, check constraints
* Inner and outer joins, subqueries, read only views and inline views
* Triggers and Java functions / stored procedures
* Many built-in functions, including XML and lossless data compression
* Wide range of data types including large objects (BLOB/CLOB) and arrays
* Sequence and autoincrement columns, computed columns (can be used for function based indexes)
* ORDER BY, GROUP BY, HAVING, UNION, OFFSET / FETCH (including PERCENT and WITH TIES), LIMIT, TOP, DISTINCT / DISTINCT ON (...)
* Window functions
* Collation support, including support for the ICU4J library
* Support for users and roles
* Compatibility modes for IBM DB2, Apache Derby, HSQLDB, MS SQL Server, MySQL, Oracle, and PostgreSQL.


* 支持多模式、信息模式
* 具有级联的参照完整性外键约束，检查约束
* 内部和外部联接、子查询、只读视图和内联视图
* 触发器和 Java 函数存储过程
* 许多内置功能，包括 XML 和无损数据压缩
* 广泛的数据类型，包括大对象 (BLOB/CLOB) 和数组
* 序列和自动增量列、计算列（可用于基于函数的索引）
* ORDER BY 、GROUP BY 、HAVING 、UNION 、OFFSET FETCH （包括 PERCENT 和 WITH TIES） 、LIMIT、TOP、DISTINCT DISTINCT ON (...)
* Window 函数
* 整理支持，包括对 ICU4J 库的支持
* 支持用户和角色
* IBM DB2、Apache Derby、HSQLDB、MS SQL Server、MySQL、Oracle 和 PostgreSQL 的兼容模式。

---

### Security Features *安全特性*

* Includes a solution for the SQL injection problem
* User password authentication uses SHA-256 and salt
* For server mode connections, user passwords are never transmitted in plain text over the network
  (even when using insecure connections; this only applies to the TCP server and not to the H2 Console however; it also doesn't apply if you set the password in the database URL)
* All database files (including script files that can be used to backup data) can be encrypted using the AES-128 encryption algorithm
* The remote JDBC driver supports TCP/IP connections over TLS
* The built-in web server supports connections over TLS
* Passwords can be sent to the database using char arrays instead of Strings


* 包括 SQL 注入问题的解决方案
* 用户密码认证使用 SHA-256 和 salt
* 对于服务器模式连接，用户密码永远不会通过网络以纯文本形式传输
  （即使使用不安全的连接；这仅适用于 TCP 服务器，但不适用于 H2 控制台；如果您在数据库 URL 中设置密码，它也不适用）
* 所有数据库文件（包括可用于备份数据的脚本文件）均可使用 AES-128 加密算法进行加密
* 远程 JDBC 驱动程序支持通过 TLS 的 TCP/IP 连接
* 内置网络服务器支持通过 TLS 的连接
* 可以使用字符数组而不是字符串将密码发送到数据库

---

### Other Features and Tools *其他特性和工具*

* Small footprint (around 2 MB), low memory requirements
* Multiple index types (b-tree, tree, hash)
* Support for multi-dimensional indexes
* CSV (comma separated values) file support
* Support for linked tables, and a built-in virtual 'range' table
* Supports the `EXPLAIN PLAN` statement; sophisticated trace options
* Database closing can be delayed or disabled to improve the performance
* Web-based Console application (translated to many languages) with autocomplete
* The database can generate SQL script files
* Contains a recovery tool that can dump the contents of the database
* Support for variables (for example to calculate running totals)
* Automatic re-compilation of prepared statements
* Uses a small number of database files
* Uses a checksum for each record and log entry for data integrity
* Well tested (high code coverage, randomized stress tests)


* 占用空间小（约 2 MB），内存要求低
* 多种索引类型（ b-tree 、tree 、 hash ）
* 支持多维索引
* CSV（ 逗号分隔值 ）文件支持
* 支持链接表和内置的虚拟 'range' 表
* 支持 `EXPLAIN PLAN` 语句；复杂的跟踪选项
* 可以延迟或禁用数据库关闭以提高性能
* 具有自动完成功能的基于 Web 的控制台应用程序（翻译成多种语言）
* 数据库可以生成 SQL 脚本文件
* 包含可以转储数据库内容的恢复工具
* 支持变量（例如计算运行总数）
* 自动重新编译准备好的语句
* 使用少量数据库文件
* 对每个记录和日志条目使用校验和以确保数据完整性
* 测试良好（高代码覆盖率，随机压力测试）

---
