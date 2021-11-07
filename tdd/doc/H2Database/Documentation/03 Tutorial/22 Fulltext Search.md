## Fulltext Search *全文搜索*

H2 includes two fulltext search implementations.
One is using Apache Lucene, and the other (the native implementation) stores the index data in special tables in the database.


H2 包括两个全文搜索实现。
一种是使用 Apache Lucene ，另一种（本机实现）将索引数据存储在数据库中的特殊表中。

----

### Using the Native Fulltext Search *使用本机全文搜索*

To initialize, call:


要初始化，请调用：

----

```sql
CREATE ALIAS IF NOT EXISTS FT_INIT FOR "org.h2.fulltext.FullText.init";
CALL FT_INIT();
```

You need to initialize it in each database where you want to use it.
Afterwards, you can create a fulltext index for a table using:


您需要在要使用它的每个数据库中对其进行初始化。
之后，您可以使用以下方法为表创建全文索引：

----

```sql
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR);
INSERT INTO TEST VALUES(1, 'Hello World');
CALL FT_CREATE_INDEX('PUBLIC', 'TEST', NULL);
```

PUBLIC is the schema name, TEST is the table name.
The list of column names (comma separated) is optional, in this case all columns are indexed.
The index is updated in realtime.
To search the index, use the following query:


PUBLIC 是模式名，TEST 是表名。
列名列表（逗号分隔）是可选的，在这种情况下，所有列都被索引。
索引实时更新。
要搜索索引，请使用以下查询：

----

```sql
SELECT * FROM FT_SEARCH('Hello', 0, 0);
```

This will produce a result set that contains the query needed to retrieve the data:


这将生成一个结果集，其中包含检索数据所需的查询：

----

```sql
QUERY: "PUBLIC"."TEST" WHERE "ID"=1
```

To drop an index on a table:


要删除表上的索引：

----

```sql
CALL FT_DROP_INDEX('PUBLIC', 'TEST');
```

To get the raw data, use `FT_SEARCH_DATA('Hello', 0, 0);`.
The result contains the columns `SCHEMA` (the schema name), `TABLE` (the table name), `COLUMNS` (an array of column names), and `KEYS` (an array of objects).
To join a table, use a join as in: `SELECT T.* FROM FT_SEARCH_DATA('Hello', 0, 0) FT, TEST T WHERE FT.TABLE='TEST' AND T.ID=FT.KEYS[0];`


要获取原始数据，请使用 `FT_SEARCH_DATA('Hello', 0, 0);` 。
结果包含列`SCHEMA`（模式名称）、`TABLE`（表名称）、`COLUMNS`（列名数组）和`KEYS`（对象数组）。
要连接表，请使用连接，如下所示： `SELECT T. FROM FT_SEARCH_DATA('Hello', 0, 0) FT, TEST T WHERE FT.TABLE='TEST' AND T.ID=FT.KEYS[0];`

----

You can also call the index from within a Java application:


您还可以从 Java 应用程序中调用索引：

----

```
org.h2.fulltext.FullText.search(conn, text, limit, offset);
org.h2.fulltext.FullText.searchData(conn, text, limit, offset);
```

### Using the Apache Lucene Fulltext Search *使用 Apache Lucene 全文搜索*

To use the Apache Lucene full text search, you need the Lucene library in the classpath.
Apache Lucene 5.5.5 or later version up to 8.0.* is required.
Newer versions may also work, but were not tested.
How to do that depends on the application; if you use the H2 Console, you can add the Lucene jar file to the environment variables `H2DRIVERS` or `CLASSPATH`.
To initialize the Lucene fulltext search in a database, call:


要使用 Apache Lucene 全文搜索，您需要类路径中的 Lucene 库。
Apache Lucene 5.5.5 或更高版本至 8.0.* 是必须的。
较新的版本也可能有效，但未经测试。
如何做到这一点取决于应用程序；如果你使用 H2 控制台，你可以将 Lucene jar 文件添加到环境变量 `H2DRIVERS` 或 `CLASSPATH` 中。

----

```sql
CREATE ALIAS IF NOT EXISTS FTL_INIT FOR "org.h2.fulltext.FullTextLucene.init";
CALL FTL_INIT();
```

You need to initialize it in each database where you want to use it.
Afterwards, you can create a full text index for a table using:


您需要在要使用它的每个数据库中对其进行初始化。
之后，您可以使用以下方法为表创建全文索引：

----

```sql
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR);
INSERT INTO TEST VALUES(1, 'Hello World');
CALL FTL_CREATE_INDEX('PUBLIC', 'TEST', NULL);
```

PUBLIC is the schema name, TEST is the table name.
The list of column names (comma separated) is optional, in this case all columns are indexed.
The index is updated in realtime.
To search the index, use the following query:


PUBLIC 是模式名，TEST 是表名。
列名列表（逗号分隔）是可选的，在这种情况下，所有列都被索引。
索引实时更新。
要搜索索引，请使用以下查询：

----

```sql
SELECT * FROM FTL_SEARCH('Hello', 0, 0);
```

This will produce a result set that contains the query needed to retrieve the data:


这将生成一个结果集，其中包含检索数据所需的查询：

----

```sql
QUERY: "PUBLIC"."TEST" WHERE "ID"=1
```

To drop an index on a table (be warned that this will re-index all of the full-text indices for the entire database):


要删除表上的索引（请注意，这将重新索引整个数据库的所有全文索引）：

---

```sql
CALL FTL_DROP_INDEX('PUBLIC', 'TEST');
```

To get the raw data, use `FTL_SEARCH_DATA('Hello', 0, 0);`.
The result contains the columns `SCHEMA` (the schema name), `TABLE` (the table name), `COLUMNS` (an array of column names), and `KEYS` (an array of objects).
To join a table, use a join as in: `SELECT T.* FROM FTL_SEARCH_DATA('Hello', 0, 0) FT, TEST T WHERE FT.TABLE='TEST' AND T.ID=FT.KEYS[0];`


要获取原始数据，请使用 `FTL_SEARCH_DATA('Hello', 0, 0);` 。
结果包含列 `SCHEMA` （模式名称）、 `TABLE` （表名称）、 `COLUMNS` （列名数组）和 `KEYS` （对象数组）。
要加入表，请使用联接，如下所示： `SELECT T.* FROM FTL_SEARCH_DATA('Hello', 0, 0) FT, TEST T WHERE FT.TABLE='TEST' AND T.ID=FT.KEYS[0];`

----

You can also call the index from within a Java application:


您还可以从 Java 应用程序中调用索引：

----

```
org.h2.fulltext.FullTextLucene.search(conn, text, limit, offset);
org.h2.fulltext.FullTextLucene.searchData(conn, text, limit, offset);
```

The Lucene fulltext search supports searching in specific column only.
Column names must be uppercase (except if the original columns are double quoted).
For column names starting with an underscore (_), another underscore needs to be added. 
Example:


Lucene 全文搜索仅支持在特定列中搜索。
列名必须大写（除非原始列是双引号的）。
对于以下划线 (_) 开头的列名，需要添加另一个下划线。
例子：

---

```sql
CREATE ALIAS IF NOT EXISTS FTL_INIT FOR "org.h2.fulltext.FullTextLucene.init";
CALL FTL_INIT();
DROP TABLE IF EXISTS TEST;
CREATE TABLE TEST(ID INT PRIMARY KEY, FIRST_NAME VARCHAR, LAST_NAME VARCHAR);
CALL FTL_CREATE_INDEX('PUBLIC', 'TEST', NULL);
INSERT INTO TEST VALUES(1, 'John', 'Wayne');
INSERT INTO TEST VALUES(2, 'Elton', 'John');
SELECT * FROM FTL_SEARCH_DATA('John', 0, 0);
SELECT * FROM FTL_SEARCH_DATA('LAST_NAME:John', 0, 0);
CALL FTL_DROP_ALL();
```
