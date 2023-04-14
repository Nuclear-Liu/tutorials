## Pluggable File System *可插拔文件系统*

This database supports a pluggable file system API.
The file system implementation is selected using a file name prefix.
Internally, the interfaces are very similar to the Java 7 NIO2 API, but do not (yet) use or require Java 7.
The following file systems are included:


该数据库支持可插拔文件系统 API。
使用文件名前缀选择文件系统实现。
在内部，这些接口与 Java 7 NIO2 API 非常相似，但（还）不使用或不需要 Java 7。
包括以下文件系统：

---

* `zip`: read-only zip-file based file system. 
  Format: `zip:~/zipFileName!/fileName`.
* `split`: file system that splits files in 1 GB files (stackable with other file systems).
* `nio`: file system that uses `FileChannel` instead of `RandomAccessFile` (faster in some operating systems).
* `nioMapped`: file system that uses memory mapped files (faster in some operating systems).
  Please note that there currently is a file size limitation of 2 GB when using this file system.
  To work around this limitation, combine it with the split file system: `split:nioMapped:~/test`.
* `async`: experimental file system that uses `AsynchronousFileChannel` instead of `RandomAccessFile` (faster in some operating systems).
* `memFS`: in-memory file system (slower than mem; experimental; mainly used for testing the database engine itself).
* `memLZF`: compressing in-memory file system (slower than memFS but uses less memory; experimental; mainly used for testing the database engine itself).
* `nioMemFS`: stores data outside of the VM's heap - useful for large memory DBs without incurring GC costs.
* `nioMemLZF`: stores compressed data outside of the VM's heap - useful for large memory DBs without incurring GC costs.
  Use "nioMemLZF:12:" to tweak the % of blocks that are stored uncompressed.
  If you size this to your working set correctly, compressed storage is roughly the same performance as uncompressed.
  The default value is 1%.


* `zip` ：基于只读 zip 文件的文件系统。
  格式：`zip:~zipFileName!fileName`。
* `split` ：将文件拆分为 1 GB 文件的文件系统（可与其他文件系统堆叠）。
* `nio` ：使用 `FileChannel` 而不是 `RandomAccessFile` 的文件系统（在某些操作系统中更快）。
* `nioMapped` ：使用内存映射文件的文件系统（在某些操作系统中更快）。
  请注意，当前使用此文件系统时文件大小限制为 2 GB。
  要解决此限制，请将其与拆分文件系统结合使用：`split:nioMapped:~test`。
* `async` ：使用 `AsynchronousFileChannel` 而不是 `RandomAccessFile`（在某些操作系统中更快）的实验性文件系统。
* `memFS` ：内存文件系统（比 mem 慢；实验性；主要用于测试数据库引擎本身）。
* `memLZF` ：压缩内存文件系统（比 memFS 慢但使用更少的内存；实验性的；主要用于测试数据库引擎本身）。
* `nioMemFS` ：将数据存储在 VM 的堆之外 - 对于大内存数据库很有用，而不会产生 GC 成本。
* `nioMemLZF` ：将压缩数据存储在 VM 堆之外 - 对大内存数据库很有用，而不会产生 GC 成本。
  使用“nioMemLZF:12:”来调整未压缩存储的块的百分比。
  如果您将它的大小正确设置为您的工作集，压缩存储的性能与未压缩的性能大致相同。
  默认值为 1%。

---

As an example, to use the `nio` file system with PageStore storage engine, use the following database URL: `jdbc:h2:nio:~/test;MV_STORE=FALSE`.
With MVStore storage engine nio file system is used by default.


例如，要将 `nio` 文件系统与 PageStore 存储引擎一起使用，请使用以下数据库 URL：`jdbc:h2:nio:~test;MV_STORE=FALSE`。
默认使用 MVStore 存储引擎 nio 文件系统。

---

To register a new file system, extend the classes `org.h2.store.fs.FilePath`, `FileBase`, and call the method `FilePath.register` before using it.


要注册一个新的文件系统，请扩展类 `org.h2.store.fs.FilePath`、 `FileBase`，并在使用之前调用方法 `FilePath.register` 。

---

For input streams (but not for random access files), URLs may be used in addition to the registered file systems.
Example: `jar:file:///c:/temp/example.zip!/org/example/nested.csv`.
To read a stream from the classpath, use the prefix `classpath`:, as in `classpath:/org/h2/samples/newsfeed.sql`.


对于输入流（但不适用于随机访问文件），除了已注册的文件系统之外，还可以使用 URL。
示例： `jar:file:c:tempexample.zip!orgexamplenested.csv` 。
要从类路径中读取流，请使用前缀 `classpath` :，如 `classpath:orgh2samplesnewsfeed.sql` 。

---
