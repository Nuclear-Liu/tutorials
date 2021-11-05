## Recursive Queries *递归查询*

H2 has experimental support for recursive queries using so called "common table expressions" (CTE).
Examples:


H2 对使用所谓的“公用表表达式”（CTE）的递归查询提供实验性支持。
例子：

---

```sql
WITH RECURSIVE T(N) AS (
    SELECT 1
    UNION ALL
    SELECT N+1 FROM T WHERE N<10
)
SELECT * FROM T;
-- returns the values 1 .. 10

WITH RECURSIVE T(N) AS (
    SELECT 1
    UNION ALL
    SELECT N*2 FROM T WHERE N<10
)
SELECT * FROM T;
-- returns the values 1, 2, 4, 8, 16

CREATE TABLE FOLDER(ID INT PRIMARY KEY, NAME VARCHAR(255), PARENT INT);

INSERT INTO FOLDER VALUES(1, null, null), (2, 'src', 1),
(3, 'main', 2), (4, 'org', 3), (5, 'test', 2);

WITH LINK(ID, NAME, LEVEL) AS (
    SELECT ID, NAME, 0 FROM FOLDER WHERE PARENT IS NULL
    UNION ALL
    SELECT FOLDER.ID, IFNULL(LINK.NAME || '/', '') || FOLDER.NAME, LEVEL + 1
    FROM LINK INNER JOIN FOLDER ON LINK.ID = FOLDER.PARENT
)
SELECT NAME FROM LINK WHERE NAME IS NOT NULL ORDER BY ID;
-- src
-- src/main
-- src/main/org
-- src/test
```

Limitations: Recursive queries need to be of the type `UNION ALL`, and the recursion needs to be on the second part of the query.
No tables or views with the name of the table expression may exist.
Different table expression names need to be used when using multiple distinct table expressions within the same transaction and for the same session.
All columns of the table expression are of type `VARCHAR`, and may need to be cast to the required data type.
Views with recursive queries are not supported.
Subqueries and `INSERT INTO ... FROM` with recursive queries are not supported.
Parameters are only supported within the last `SELECT` statement (a workaround is to use session variables like `@start` within the table expression).
The syntax is:


限制：递归查询需要是“UNION ALL”类型，并且递归需要在查询的第二部分。
可能不存在具有表表达式名称的表或视图。
在同一事务中和同一会话中使用多个不同的表表达式时，需要使用不同的表表达式名称。
表表达式的所有列都是 `VARCHAR` 类型，可能需要转换为所需的数据类型。
不支持具有递归查询的视图。
不支持带有递归查询的子查询和 `INSERT INTO ... FROM` 。
参数仅在最后一个 `SELECT` 语句中受支持（一种解决方法是在表表达式中使用像 `@start` 这样的会话变量）。
语法是：

---

```sql
WITH RECURSIVE recursiveQueryName(columnName, ...) AS (
    nonRecursiveSelect
    UNION ALL
    recursiveSelect
)
select
```
