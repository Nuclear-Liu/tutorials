## User-Defined Variables *用户定义的变量*

This database supports user-defined variables.
Variables start with `@` and can be used wherever expressions or parameters are allowed.
Variables are not persisted and session scoped, that means only visible from within the session in which they are defined.
A value is usually assigned using the SET command:


该数据库支持用户定义的变量。
变量以 `@` 开头，可以在任何允许表达式或参数的地方使用。
变量不是持久化的，也不是会话范围的，这意味着只能在定义它们的会话中可见。
通常使用 SET 命令分配一个值：

---

```sql
SET @USER = 'Joe';
```

The value can also be changed using the SET() method. 
This is useful in queries:


也可以使用 SET() 方法更改该值。
这在查询中很有用：

---

```sql
SET @TOTAL = NULL;
SELECT X, SET(@TOTAL, IFNULL(@TOTAL, 1.) * X) F FROM SYSTEM_RANGE(1, 50);
```

Variables that are not set evaluate to `NULL`.
The data type of a user-defined variable is the data type of the value assigned to it, that means it is not necessary (or possible) to declare variable names before using them.
There are no restrictions on the assigned values; large objects (LOBs) are supported as well.
Rolling back a transaction does not affect the value of a user-defined variable.


未设置的变量评估为 `NULL` 。
用户定义变量的数据类型是分配给它的值的数据类型，这意味着在使用它们之前没有必要（或不可能）声明变量名称。
对赋值没有限制；也支持大对象 (LOB)。
回滚事务不会影响用户定义变量的值。

---
