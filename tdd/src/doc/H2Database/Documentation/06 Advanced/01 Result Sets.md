## Result Sets *结果集*

### Statements that Return a Result Set *返回结果集的语句*

The following statements return a result set: `SELECT`, `TABLE`, `VALUES`, `EXPLAIN`, `CALL`, `SCRIPT`, `SHOW`, `HELP`.
`EXECUTE` may return either a result set or an update count.
Result of a `WITH` statement depends on inner command.
All other statements return an update count.


以下语句返回结果集：`SELECT`、`TABLE`、`VALUES`、`EXPLAIN`、`CALL`、`SCRIPT`、`SHOW`、`HELP`。
`EXECUTE` 可以返回结果集或更新计数。
`WITH` 语句的结果取决于内部命令。
所有其他语句返回更新计数。

---

### Limiting the Number of Rows *限制行数*

Before the result is returned to the application, all rows are read by the database. Server side cursors are not supported currently.
If only the first few rows are interesting for the application, then the result set size should be limited to improve the performance.
This can be done using `FETCH` in a query (example: `SELECT * FROM TEST FETCH FIRST 100 ROWS ONLY`), or by using `Statement.setMaxRows(max)`.


在将结果返回给应用程序之前，数据库会读取所有行。当前不支持服务器端游标。
如果应用程序只对前几行感兴趣，则应限制结果集大小以提高性能。
这可以在查询中使用 `FETCH` （例如： `SELECT FROM TEST FETCH FIRST 100 ROWS ONLY` ）或使用 `Statement.setMaxRows(max)` 来完成。

---

### Large Result Sets and External Sorting *大型结果集和外部排序*

For large result set, the result is buffered to disk.
The threshold can be defined using the statement `SET MAX_MEMORY_ROWS`.
If `ORDER BY` is used, the sorting is done using an external sort algorithm.
In this case, each block of rows is sorted using quick sort, then written to disk; when reading the data, the blocks are merged together.


对于大型结果集，结果被缓冲到磁盘。
可以使用语句 `SET MAX_MEMORY_ROWS` 来定义阈值。
如果使用 `ORDER BY` ，则使用外部排序算法进行排序。
在这种情况下，每个行块都使用快速排序进行排序，然后写入磁盘；读取数据时，块合并在一起。

---
