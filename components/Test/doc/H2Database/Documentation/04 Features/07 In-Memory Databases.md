## In-Memory Databases *内存数据库*

For certain use cases (for example: rapid prototyping, testing, high performance operations, read-only databases), it may not be required to persist data, or persist changes to the data.
This database supports the in-memory mode, where the data is not persisted.


对于某些用例（例如：快速原型设计、测试、高性能操作、只读数据库），可能不需要持久化数据或持久化对数据的更改。
该数据库支持内存模式，其中数据不持久化

---

In some cases, only one connection to a in-memory database is required.
This means the database to be opened is private.
In this case, the database URL is `jdbc:h2:mem:` Opening two connections within the same virtual machine means opening two different (private) databases.


在某些情况下，只需要**一个到内存数据库的连接**。
这意味着要打开的数据库是**私有**的。
在这种情况下，数据库 URL 是 `jdbc:h2:mem:` 在同一个虚拟机中打开两个连接意味着打开**两个不同的（私有）数据库**。

---

Sometimes multiple connections to the same in-memory database are required.
In this case, the database URL must include a name.
Example: `jdbc:h2:mem:db1`.
Accessing the same database using this URL only works within the same virtual machine and class loader environment.


有时需要**多个连接到同一个内存数据库**。
在这种情况下，数据库 URL 必须包含**名称**。
示例： `jdbc:h2:mem:db1` 。
**使用此 URL 访问相同的数据库仅适用于相同的虚拟机和类加载器环境**。

---

To access an in-memory database from another process or from another computer, you need to start a TCP server in the same process as the in-memory database was created.
The other processes then need to access the database over TCP/IP or TLS, using a database URL such as: `jdbc:h2:tcp://localhost/mem:db1`.


要从**另一个进程**或**另一台计算机访问内存数据库**，您需要**在创建内存数据库的同一进程中启动 TCP 服务器**。
然后其他进程需要通过 TCP/IP 或 TLS 访问数据库，使用数据库 URL，例如： `jdbc:h2:tcp:localhostmem:db1` 。

---

By default, closing the last connection to a database closes the database.
For an in-memory database, this means the content is lost.
To keep the database open, add `;DB_CLOSE_DELAY=-1` to the database URL.
To keep the content of an in-memory database as long as the virtual machine is alive, use `jdbc:h2:mem:test;DB_CLOSE_DELAY=-1`. 


默认情况下，**关闭与数据库的最后一个连接将关闭该数据库**。
对于内存数据库，这意味着内容丢失。
要**保持数据库打开**，请将 `;DB_CLOSE_DELAY=-1` 添加到数据库 URL。
只要虚拟机处于活动状态，要保持内存数据库的内容，请使用 `jdbc:h2:mem:test;DB_CLOSE_DELAY=-1` 。

---
