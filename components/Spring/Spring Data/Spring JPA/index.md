# Spring JPA

## Soft Delete _软删除_

### Entity Class

* `@SQLDelete` 用于删除实体/集合的自定义 SQL 语句
  * `sql` 过程名称或 SQL DELETE 语句
  * `callable` (默认 `false` ) 语句是否可以调用
  * `check` (默认 `ResultCheckStyle.NONE`) 对于持久性操作，将使用哪种样式来确定结果（成功/失败）
* `@Where` 要添加到集合的元素实体或目标实体的 `where` 子句。该子句是用 SQL 编写的。此处常用于**软删除**
  * `clause` `where` 子句谓词

example: 
```jshelllanguage
@SQLDelete(sql = "UPDATE table_name SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
```

