## Cache Settings *缓存设置*

The database keeps most frequently used data in the main memory.
The amount of memory used for caching can be changed using the setting `CACHE_SIZE`.
This setting can be set in the database connection URL (`jdbc:h2:~/test;CACHE_SIZE=131072`), or it can be changed at runtime using `SET CACHE_SIZE size`.
The size of the cache, as represented by `CACHE_SIZE` is measured in KB, with each KB being 1024 bytes.
This setting has no effect for in-memory databases.
For persistent databases, the setting is stored in the database and re-used when the database is opened the next time.
However, when opening an existing database, the cache size is set to at most half the amount of memory available for the virtual machine (Runtime.getRuntime().maxMemory()), even if the cache size setting stored in the database is larger; however the setting stored in the database is kept.
Setting the cache size in the database URL or explicitly using `SET CACHE_SIZE` overrides this value (even if larger than the physical memory).
To get the current used maximum cache size, use the query `SELECT * FROM INFORMATION_SCHEMA.SETTINGS WHERE NAME = 'info.CACHE_MAX_SIZE'`


数据库将最常用的数据保存在主内存中。
可以使用设置 `CACHE_SIZE` 更改用于缓存的内存量。
此设置可以在数据库连接 URL ( `jdbc:h2:~test;CACHE_SIZE=131072` ) 中设置，也可以在运行时使用 `SET CACHE_SIZE size` 更改。
缓存的大小，由 `CACHE_SIZE` 表示，以KB为单位，每个KB为1024字节。
此设置对内存数据库没有影响。
对于持久性数据库，该设置存储在数据库中，并在下次打开数据库时重新使用。
但是，在打开现有数据库时，缓存大小最多设置为虚拟机可用内存量的一半 (Runtime.getRuntime().maxMemory())，即使存储在数据库中的缓存大小设置更大;但是存储在数据库中的设置被保留。
在数据库 URL 中设置缓存大小或显式使用“SET CACHE_SIZE”会覆盖此值（即使大于物理内存）。
要获取当前使用的最大缓存大小，请使用查询 `SELECT * FROM INFORMATION_SCHEMA.SETTINGS WHERE NAME = 'info.CACHE_MAX_SIZE'`

---

An experimental scan-resistant cache algorithm "Two Queue" (2Q) is available.
To enable it, append `;CACHE_TYPE=TQ` to the database URL.
The cache might not actually improve performance.
If you plan to use it, please run your own test cases first.


可以使用实验性抗扫描缓存算法“双队列”（2Q）。
要启用它，请将 `;CACHE_TYPE=TQ` 附加到数据库 URL。
缓存实际上可能不会提高性能。
如果您打算使用它，请先运行您自己的测试用例。

---

Also included is an experimental second level soft reference cache.
Rows in this cache are only garbage collected on low memory.
By default the second level cache is disabled.
To enable it, use the prefix `SOFT_`.
Example: `jdbc:h2:~/test;CACHE_TYPE=SOFT_LRU`.
The cache might not actually improve performance.
If you plan to use it, please run your own test cases first.


还包括一个实验性的二级软引用缓存。
此缓存中的行仅在低内存上进行垃圾收集。
默认情况下，禁用二级缓存。
要启用它，请使用前缀 `SOFT_` 。
示例： `jdbc:h2:~test;CACHE_TYPE=SOFT_LRU` 。
缓存实际上可能不会提高性能。
如果您打算使用它，请先运行您自己的测试用例。

---

To get information about page reads and writes, and the current caching algorithm in use, call `SELECT * FROM INFORMATION_SCHEMA.SETTINGS`.
The number of pages read / written is listed. 


要获取有关页面读取和写入以及当前使用的缓存算法的信息，请调用“SELECT FROM INFORMATION_SCHEMA.SETTINGS”。
列出了读取写入的页数。

---
