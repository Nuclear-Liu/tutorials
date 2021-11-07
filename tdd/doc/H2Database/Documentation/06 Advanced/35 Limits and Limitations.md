## Limits and Limitations *限制和局限*

This database has the following known limitations:


此数据库具有以下已知限制：

---

* Database file size limit: 4 TB (using the default page size of 2 KB) or higher (when using a larger page size).
  This limit is including CLOB and BLOB data.
* The maximum file size for FAT or FAT32 file systems is 4 GB.
  That means when using FAT or FAT32, the limit is 4 GB for the data.
  This is the limitation of the file system.
  The database does provide a workaround for this problem, it is to use the file name prefix `split:`.
  In that case files are split into files of 1 GB by default.
  An example database URL is: `jdbc:h2:split:~/test`.
* The maximum number of rows per table is 2^64.
* The maximum number of open transactions is 65535.
* Main memory requirements:
  The larger the database, the more main memory is required.
  With the current storage mechanism (the page store), the minimum main memory required is around 1 MB for each 8 GB database file size.
* Limit on the complexity of SQL statements.
  Statements of the following form will result in a stack overflow exception:


* 数据库文件大小限制：4 TB（使用 2 KB 的默认页面大小）或更高（使用更大的页面大小时）。
  此限制包括 CLOB 和 BLOB 数据。
* FAT 或 FAT32 文件系统的最大文件大小为 4 GB。
  这意味着在使用 FAT 或 FAT32 时，数据的限制为 4 GB。
  这是文件系统的限制。
  数据库确实为这个问题提供了一种解决方法，它是使用文件名前缀 `split:` 。
  在这种情况下，默认情况下文件将拆分为 1 GB 的文件。
  一个示例数据库 URL 是： `jdbc:h2:split:~test` 。
* 每个表的最大行数为 2^64。
* 最大未结交易数为 65535。
* 最大未结交易数为 65535。
* 主内存要求：
  数据库越大，需要的主内存就越多。
  使用当前的存储机制（页面存储），对于每个 8 GB 的数据库文件大小，所需的最小主内存约为 1 MB。
* 限制 SQL 语句的复杂性。
  以下形式的语句将导致堆栈溢出异常：
  
---

```sql
SELECT * FROM DUAL WHERE X = 1
OR X = 2 OR X = 2 OR X = 2 OR X = 2 OR X = 2
-- repeat previous line 500 times --
```

* There is no limit for the following entities, except the memory and storage capacity:
  maximum identifier length (table name, column name, and so on); 
  maximum number of tables, columns, indexes, triggers, and other database objects; 
  maximum statement length, number of parameters per statement, tables per statement, expressions in order by, group by, having, and so on; 
  maximum rows per query; maximum columns per table, columns per index, indexes per table, lob columns per table, and so on; 
  maximum row length, index row length, select row length; 
  maximum length of a varchar column, decimal column, literal in a statement.
* Querying from the metadata tables is slow if there are many tables (thousands).
* For limitations on data types, see the documentation of the respective Java data type or the data type documentation of this database. 


* 除内存和存储容量外，以下实体没有限制：
  最大标识符长度（表名、列名等）；
  表、列、索引、触发器和其他数据库对象的最大数量；
  最大语句长度、每条语句的参数数、每条语句的表、按顺序、分组、具有等的表达式；
  每个查询的最大行数；每个表的最大列数、每个索引的列数、每个表的索引数、每个表的 lob 列数等；
  最大行长、索引行长、选择行长；
  语句中 varchar 列、十进制列、文字的最大长度。
* 如果有很多表（数千个），从元数据表中查询会很慢。
* 有关数据类型的限制，请参阅相应 Java 数据类型的文档或此数据库的数据类型文档。

---
