## Using H2 within NetBeans *在 NetBeans 中使用 H2*

There is a known issue when using the Netbeans SQL Execution Window: before executing a query, another query in the form `SELECT COUNT(*) FROM <query>` is run.
This is a problem for queries that modify state, such as `SELECT NEXT VALUE FOR SEQ`.
In this case, two sequence values are allocated instead of just one.


使用 Netbeans SQL 执行窗口时存在一个已知问题：在执行查询之前，将运行 `SELECT COUNT(*) FROM <query>` 形式的另一个查询。
这是修改状态的查询的问题，例如 `SELECT NEXT VALUE FOR SEQ` 。
在这种情况下，分配了两个序列值而不是一个。

----
