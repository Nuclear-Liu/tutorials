## Multi-Dimensional Indexes *多维索引*

A tool is provided to execute efficient multi-dimension (spatial) range queries.
This database does not support a specialized spatial index (R-Tree or similar).
Instead, the B-Tree index is used.
For each record, the multi-dimensional key is converted (mapped) to a single dimensional (scalar) value.
This value specifies the location on a space-filling curve.


提供了一种工具来执行高效的多维（空间）范围查询。
该数据库不支持专门的空间索引（R-Tree 或类似的）。
相反，使用 B 树索引。
对于每条记录，多维键被转换（映射）为单维（标量）值。
此值指定空间填充曲线上的位置。

---

Currently, Z-order (also called N-order or Morton-order) is used; Hilbert curve could also be used, but the implementation is more complex.
The algorithm to convert the multi-dimensional value is called bit-interleaving.
The scalar value is indexed using a B-Tree index (usually using a computed column).


目前使用的是 Z-order（也称为 N-order 或 Morton-order）；也可以使用希尔伯特曲线，但实现起来更复杂。
转换多维值的算法称为位交织。
标量值使用 B 树索引（通常使用计算列）进行索引。

---

The method can result in a drastic performance improvement over just using an index on the first column.
Depending on the data and number of dimensions, the improvement is usually higher than factor 5.
The tool generates a SQL query from a specified multi-dimensional range.
The method used is not database dependent, and the tool can easily be ported to other databases.
For an example how to use the tool, please have a look at the sample code provided in `TestMultiDimension.java`. 


与仅在第一列上使用索引相比，该方法可以显着提高性能。
根据数据和维数，改进通常高于因子 5。
该工具从指定的多维范围生成 SQL 查询。
使用的方法不依赖于数据库，并且该工具可以轻松移植到其他数据库。
有关如何使用该工具的示例，请查看 `TestMultiDimension.java` 中提供的示例代码。

---
