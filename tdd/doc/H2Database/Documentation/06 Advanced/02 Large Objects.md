## Large Objects *大对象*

### Storing and Reading Large Objects *存储和读取大对象*

If it is possible that the objects don't fit into memory, then the data type CLOB (for textual data) or BLOB (for binary data) should be used.
For these data types, the objects are not fully read into memory, by using streams.
To store a BLOB, use `PreparedStatement.setBinaryStream`.
To store a CLOB, use `PreparedStatement.setCharacterStream`.
To read a BLOB, use `ResultSet.getBinaryStream`, and to read a CLOB, use `ResultSet.getCharacterStream`.
When using the client/server mode, large BLOB and CLOB data is stored in a temporary file on the client side.


如果对象可能不适合内存，则应使用数据类型 CLOB（用于文本数据）或 BLOB（用于二进制数据）。
对于这些数据类型，对象不会通过使用流完全读入内存。
要存储 BLOB，请使用 `PreparedStatement.setBinaryStream` 。
要存储 CLOB，请使用 `PreparedStatement.setCharacterStream` 。
要读取 BLOB，使用 `ResultSet.getBinaryStream` ，读取 CLOB，使用 `ResultSet.getCharacterStream` 。
使用客户端/服务器模式时，大的BLOB和CLOB数据存储在客户端的临时文件中。
---

### When to use CLOB/BLOB *何时使用 CLOB/BLOB*

By default, this database stores large LOB (CLOB and BLOB) objects separate from the main table data.
Small LOB objects are stored in-place, the threshold can be set using [MAX_LENGTH_INPLACE_LOB](http://h2database.com/html/commands.html#set_max_length_inplace_lob), but there is still an overhead to use CLOB/BLOB.
Because of this, BLOB and CLOB should never be used for columns with a maximum size below about 200 bytes.
The best threshold depends on the use case; reading in-place objects is faster than reading from separate files, but slows down the performance of operations that don't involve this column.


默认情况下，此数据库存储与主表数据分开的大型 LOB（CLOB 和 BLOB）对象。
小型 LOB 对象就地存储，可以使用 [MAX_LENGTH_INPLACE_LOB](http:h2database.comhtmlcommands.htmlset_max_length_inplace_lob) 设置阈值，但使用 CLOB/BLOB 仍然存在开销。
因此，BLOB 和 CLOB 永远不应用于最大大小低于约 200 字节的列。
最佳阈值取决于用例；读取就地对象比从单独的文件读取要快，但会降低不涉及此列的操作的性能。

---

### Large Object Compression *大对象压缩*

The following feature is only available for the PageStore storage engine.
For the MVStore engine (the default for H2 version 1.4.x), append `;COMPRESS=TRUE` to the database URL instead.
CLOB and BLOB values can be compressed by using [SET COMPRESS_LOB]().
The LZF algorithm is faster but needs more disk space.
By default compression is disabled, which usually speeds up write operations.
If you store many large compressible values such as XML, HTML, text, and uncompressed binary files, then compressing can save a lot of disk space (sometimes more than 50%), and read operations may even be faster.


以下功能仅适用于 PageStore 存储引擎。
对于 MVStore 引擎（H2 版本 1.4.x 的默认设置），改为将 `;COMPRESS=TRUE` 附加到数据库 URL。
可以使用 [SET COMPRESS_LOB]() 压缩 CLOB 和 BLOB 值。
LZF 算法速度更快，但需要更多磁盘空间。
默认情况下禁用压缩，这通常会加快写入操作。
如果存储很多大型的可压缩值，如 XML、HTML、文本和未压缩的二进制文件，那么压缩可以节省大量磁盘空间（有时超过 50%），读取操作甚至可能更快。

---
