## Java Management Extension (JMX) *Java管理扩展(JMX)*

Management over JMX is supported, but not enabled by default. 
To enable JMX, append ;`JMX=TRUE` to the database URL when opening the database.
Various tools support JMX, one such tool is the `jconsole`.
When opening the `jconsole`, connect to the process where the database is open (when using the server mode, you need to connect to the server process).
Then go to the `MBeans` section. 
Under `org.h2` you will find one entry per database.
The object name of the entry is the database short name, plus the path (each colon is replaced with an underscore character).


支持通过 JMX 进行管理，但默认情况下不启用。
要启用 JMX，请在打开数据库时将 ;`JMX=TRUE` 附加到数据库 URL。
各种工具都支持 JMX，其中一种工具是 `jconsole` 。
打开 `jconsole` 时，连接到打开数据库的进程（使用服务器模式时，需要连接到服务器进程）。
然后转到“MBeans”部分。
在 `org.h2` 下，您会发现每个数据库都有一个条目。
条目的对象名称是数据库短名称加上路径（每个冒号都用下划线字符替换）。

---

The following attributes and operations are supported:


支持以下属性和操作：

---

* `CacheSize`: the cache size currently in use in KB.
* `CacheSizeMax` (read/write): the maximum cache size in KB.
* `Exclusive`: whether this database is open in exclusive mode or not.
* `FileReadCount`: the number of file read operations since the database was opened.
* `FileSize`: the file size in KB.
* `FileWriteCount`: the number of file write operations since the database was opened.
* `FileWriteCountTotal`: the number of file write operations since the database was created.
* `LogMode` (read/write): the current transaction log mode. See `SET LOG` for details.
* `Mode`: the compatibility mode (`REGULAR` if no compatibility mode is used).
* `MultiThreaded`: true if multi-threaded is enabled.
* `Mvcc`: true if `MVCC` is enabled.
* `ReadOnly`: true if the database is read-only.
* `TraceLevel` (read/write): the file trace level.
* `Version`: the database version in use.
* `listSettings`: list the database settings.
* `listSessions`: list the open sessions, including currently executing statement (if any) and locked tables (if any).


* `CacheSize` ：当前使用的缓存大小，以 KB 为单位。
* `CacheSizeMax` （读写）：以 KB 为单位的最大缓存大小。
* `Exclusive` ：此数据库是否以独占模式打开。
* `FileReadCount` ：自数据库打开以来文件读取操作的次数。
* `FileSize` ：以 KB 为单位的文件大小。
* `FileWriteCount` ：自数据库打开以来的文件写入操作数。
* `FileWriteCountTotal` ：自数据库创建以来的文件写入操作数。
* `LogMode` （读写）：当前事务日志模式。有关详细信息，请参阅 `SET LOG` 。
* `Mode` ：兼容模式（如果没有使用兼容模式，则为 `REGULAR` ）。
* `MultiThreaded` ：如果启用了多线程，则为 true 。
* `Mvcc` ：如果启用了 `MVCC` ，则为 true 。
* `ReadOnly` ：如果数据库是只读的，则为 true 。
* `TraceLevel` （读写）：文件跟踪级别。
* `Version` ：正在使用的数据库版本。
* `listSettings` ：列出数据库设置。
* `listSessions` ：列出打开的会话，包括当前正在执行的语句（如果有）和锁定的表（如果有）。

---

To enable JMX, you may need to set the system properties `com.sun.management.jmxremote` and `com.sun.management.jmxremote.port` as required by the JVM. 


要启用 JMX，您可能需要根据 JVM 的要求设置系统属性 `com.sun.management.jmxremote` 和 `com.sun.management.jmxremote.port` 。

---
