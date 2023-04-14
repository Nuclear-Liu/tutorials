## Computed Columns / Function Based Index *基于计算列/函数索引*

A computed column is a column whose value is calculated before storing.
The formula is evaluated when the row is inserted, and re-evaluated every time the row is updated.
One use case is to automatically update the last-modification time:


计算列是在存储之前计算其值的列。
在插入行时计算公式，并在每次更新行时重新计算。
一种用例是自动更新上次修改时间：

---

```sql
CREATE TABLE TEST(
    ID INT,
    NAME VARCHAR,
    LAST_MOD TIMESTAMP WITH TIME ZONE AS CURRENT_TIMESTAMP
);
```

Function indexes are not directly supported by this database, but they can be emulated by using computed columns.
For example, if an index on the upper-case version of a column is required, create a computed column with the upper-case version of the original column, and create an index for this column:


此数据库不直接支持函数索引，但可以使用计算列来模拟它们。
例如，如果需要对列的大写版本建立索引，则使用原始列的大写版本创建计算列，并为此列创建索引：

---

```sql
CREATE TABLE ADDRESS(
    ID INT PRIMARY KEY,
    NAME VARCHAR,
    UPPER_NAME VARCHAR AS UPPER(NAME)
);
CREATE INDEX IDX_U_NAME ON ADDRESS(UPPER_NAME);
```

When inserting data, it is not required (and not allowed) to specify a value for the upper-case version of the column, because the value is generated.
But you can use the column when querying the table:


插入数据时，不需要（也不允许）为列的大写版本指定值，因为该值是生成的。
但是您可以在查询表时使用该列：

---

```sql
INSERT INTO ADDRESS(ID, NAME) VALUES(1, 'Miller');
SELECT * FROM ADDRESS WHERE UPPER_NAME='MILLER';
```
