## Android *Android*

You can use this database on an Android device (using the Dalvik VM) instead of or in addition to SQLite.
So far, only very few tests and benchmarks were run, but it seems that performance is similar to SQLite, except for opening and closing a database, which is not yet optimized in H2 (H2 takes about 0.2 seconds, and SQLite about 0.02 seconds).
Read operations seem to be a bit faster than SQLite, and write operations seem to be slower.
So far, only very few tests have been run, and everything seems to work as expected.
Fulltext search was not yet tested, however the native fulltext search should work.


您可以在 Android 设备（使用 Dalvik VM）上使用此数据库来代替 SQLite 或作为 SQLite 的补充。
到目前为止，只运行了很少的测试和基准测试，但似乎性能与 SQLite 相似，除了打开和关闭数据库，在 H2 中尚未优化（ H2 大约需要 0.2 秒， SQLite 大约 0.02 秒）。
读操作似乎比 SQLite 快一点，写操作似乎更慢。
到目前为止，只运行了很少的测试，一切似乎都按预期工作。
全文搜索尚未测试，但本机全文搜索应该可以工作。

---

Reasons to use H2 instead of SQLite are:


使用 H2 而不是 SQLite 的原因是：

----

* Full Unicode support including UPPER() and LOWER().
* Streaming API for BLOB and CLOB data.
* Fulltext search.
* Multiple connections.
* User defined functions and triggers.
* Database file encryption.
* Reading and writing CSV files (this feature can be used outside the database as well).
* Referential integrity and check constraints.
* Better data type and SQL support.
* In-memory databases, read-only databases, linked tables.
* Better compatibility with other databases which simplifies porting applications.
* Possibly better performance (so far for read operations).
* Server mode (accessing a database on a different machine over TCP/IP).


* 完整的 Unicode 支持，包括 UPPER() 和 LOWER()。
* 用于 BLOB 和 CLOB 数据的 Streaming API 。
* 全文检索。
* 多个连接。
* 用户定义的函数和触发器。
* 数据库文件加密。
* 读取和写入 CSV 文件（此功能也可以在数据库外使用）。
* 参照完整性和检查约束。
* 更好的数据类型和 SQL 支持。
* 内存数据库、只读数据库、链接表。
* 与其他数据库更好的兼容性，简化了移植应用程序。
* 可能有更好的性能（到目前为止读取操作）。
* 服务器模式（通过 TCPIP 访问不同机器上的数据库）。

----

Currently only the JDBC API is supported (it is planned to support the Android database API in future releases).
Both the regular H2 jar file and the smaller `h2small-*.jar` can be used.
To create the smaller jar file, run the command `./build.sh jarSmall` (Linux / Mac OS) or `build.bat jarSmall` (Windows).


目前仅支持 JDBC API （计划在未来版本中支持 Android 数据库 API ）。
常规的 H2 jar 文件和较小的 `h2small-*.jar` 都可以使用。
要创建较小的 jar 文件，请运行命令 `./build.sh jarSmall` (Linux / Mac OS) 或 `build.bat jarSmall` (Windows)。

----

The database files needs to be stored in a place that is accessible for the application. 
Example:


数据库文件需要存储在应用程序可以访问的地方。
例子：

----

```
String url = "jdbc:h2:/data/data/" +
    "com.example.hello" +
    "/data/hello" +
    ";FILE_LOCK=FS" +
    ";PAGE_SIZE=1024" +
    ";CACHE_SIZE=8192";
conn = DriverManager.getConnection(url);
...
```

Limitations: Using a connection pool is currently not supported, because the required `javax.sql.` classes are not available on Android.


限制：当前不支持使用连接池，因为所需的 `javax.sql.` 类在 Android 上不可用。

----
