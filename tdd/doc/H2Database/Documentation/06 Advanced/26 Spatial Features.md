## Spatial Features *空间特征*

H2 supports the geometry data type and spatial indexes.
Here is an example SQL script to create a table with a spatial column and index:


H2 支持几何数据类型和空间索引。
下面是一个 SQL 脚本示例，用于创建一个带有空间列和索引的表：

---

```sql
CREATE TABLE GEO_TABLE(GID SERIAL, THE_GEOM GEOMETRY);
INSERT INTO GEO_TABLE(THE_GEOM) VALUES
    ('POINT(500 505)'),
    ('LINESTRING(550 551, 525 512, 565 566)'),
    ('POLYGON ((550 521, 580 540, 570 564, 512 566, 550 521))');
CREATE SPATIAL INDEX GEO_TABLE_SPATIAL_INDEX
    ON GEO_TABLE(THE_GEOM);
```

To query the table using geometry envelope intersection, use the operation `&&`, as in PostGIS:


要使用几何包络交集查询表，请使用操作 `&&`，如在 PostGIS 中：

---

```sql
SELECT * FROM GEO_TABLE
    WHERE THE_GEOM &&
    'POLYGON ((490 490, 536 490, 536 515, 490 515, 490 490))';
```

You can verify that the spatial index is used using the "explain plan" feature:


您可以使用“解释计划”功能验证是否使用了空间索引：

---

```sql
EXPLAIN SELECT * FROM GEO_TABLE
    WHERE THE_GEOM &&
    'POLYGON ((490 490, 536 490, 536 515, 490 515, 490 490))';
-- Result
SELECT
    "GEO_TABLE"."GID",
    "GEO_TABLE"."THE_GEOM"
FROM "PUBLIC"."GEO_TABLE"
    /* PUBLIC.GEO_TABLE_SPATIAL_INDEX:
    THE_GEOM &&
    'POLYGON ((490 490, 536 490, 536 515, 490 515, 490 490))'::Geometry */
WHERE INTERSECTS("THE_GEOM",
    'POLYGON ((490 490, 536 490, 536 515, 490 515, 490 490))'::Geometry)
```

For persistent databases, the spatial index is stored on disk; for in-memory databases, the index is kept in memory.


对于持久性数据库，空间索引存储在磁盘上；对于内存数据库，索引保存在内存中。

---
