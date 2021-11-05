## Keywords / Reserved Words *关键字 / 保留字*

There is a list of keywords that can't be used as identifiers (table names, column names and so on), unless they are quoted (surrounded with double quotes).
The following tokens are keywords in H2:


有一个关键字列表不能用作标识符（表名、列名等），除非它们被引用（用双引号括起来）。
以下 token 是H2中的关键词

---

| Keyword | H2 | SQL:2016 | SQL:2011 | SQL:2008 | SQL:2003 | SQL:1999 | SQL-92 |
| ---- | ---- | ----- | ---- | ---- | ---- | ---- | ---- |
| ALL | + | + | + | + | + | + | + |
| AND | CS | + | + | + | + | + | + |
| ARRAY | + | + | + | + | + | + |  |
| AS | CS | + | + | + | + | + | + |
| BETWEEN | CS | + | + | + | + | NR | + |
| BOTH | CS | + | + | + | + | + | + |
| CASE | + | + | + | + | + | + | + |
| CHECK | + | + | + | + | + | + | + |
| CONSTRAINT | + | + | + | + | + | + | + |
| CROSS | + | + | + | + | + | + | + |
| CURRENT_CATALOG | + | + | + | + |  |  |  |
| CURRENT_DATE | + | + | + | + | + | + | + |
| CURRENT_SCHEMA | + | + | + | + |  |  |  |
| CURRENT_TIME | + | + | + | + | + | + | + |
| CURRENT_TIMESTAMP | + | + | + | + | + | + | + |
| CURRENT_USER | + | + | + | + | + | + | + |
| DISTINCT | + | + | + | + | + | + | + |
| EXCEPT | + | + | + | + | + | + | + |
| EXISTS | + | + | + | + | + | NR | + |
| FALSE | + | + | + | + | + | + | + |
| FETCH | + | + | + | + | + | + | + |
| FILTER | CS | + | + | + | + |  |  |
| FOR | + | + | + | + | + | + | + |
| FOREIGN | + | + | + | + | + | + | + |
| FROM | + | + | + | + | + | + | + |
| FULL | + | + | + | + | + | + | + |
| GROUP | + | + | + | + | + | + | + |
| GROUPS | CS | + | + |  |  |  |  |
| HAVING | + | + | + | + | + | + | + |
| IF | + |  |  |  |  |  |  |
| ILIKE | CS |  |  |  |  |  |  |
| IN | CS | + | + | + | + | + | + |
| INNER | + | + | + | + | + | + | + |
| INTERSECT | + | + | + | + | + | + | + |
| INTERSECTS | + |  |  |  |  |  |  |
| INTERVAL | + | + | + | + | + | + | + |
| IS | + | + | + | + | + | + | + |
| JOIN | + | + | + | + | + | + | + |
| LEADING | CS | + | + | + | + | + | + |
| LEFT | + | + | + | + | + | + | + |
| LIKE | + | + | + | + | + | + | + |
| LIMIT | + |  |  |  |  | + |  |
| LOCALTIME | + | + | + | + | + | + |  |
| LOCALTIMESTAMP | + | + | + | + | + | + |  |
| MINUS | + |  |  |  |  |  |  |
| NATURAL | + | + | + | + | + | + | + |
| NATURAL | + | + | + | + | + | + | + |
| NOT | + | + | + | + | + | + | + |
| NULL | + | + | + | + | + | + | + |
| OFFSET | + | + | + | + |  |  |  |
| ON | + | + | + | + | + | + | + |
| OR | CS | + | + | + | + | + | + |
| ORDER | + | + | + | + | + | + | + |
| OVER | CS | + | + | + | + |  |  |
| PARTITION | CS | + | + | + | + |  |  |
| PRIMARY | + | + | + | + | + | + | + |
| QUALIFY | + |  |  |  |  |  |  |
| RANGE | CS | + | + | + | + |  |  |
| REGEXP | CS |  |  |  |  |  |  |
| RIGHT | + | + | + | + | + | + | + |
| ROW | + | + | + | + | + | + |  |
| _ROWID_ | + |  |  |  |  |  |  |
| ROWNUM | + |  |  |  |  |  |  |
| ROWS | CS | + | + | + | + | + | + |
| SELECT | + | + | + | + | + | + | + |
| SYSDATE | CS |  |  |  |  |  |  |
| SYSTIME | CS |  |  |  |  |  |  |
| SYSTIMESTAMP | CS |  |  |  |  |  |  |
| TABLE | + | + | + | + | + | + | + |
| TODAY | CS |  |  |  |  |  |  |
| TOP | CS |  |  |  |  |  |  |
| TRAILING | CS | + | + | + | + | + | + |
| TRUE | + | + | + | + | + | + | + |
| UNION | + | + | + | + | + | + | + |
| UNIQUE | + | + | + | + | + | + | + |
| UNKNOWN | + | + | + | + | + | + | + |
| USING | + | + | + | + | + | + | + |
| VALUES | + | + | + | + | + | + | + |
| WHERE | + | + | + | + | + | + | + |
| WITH | + | + | + | + | + | + | + |

Some keywords in H2 are context-sensitive (CS), such keywords may be used as identifiers in some places, but cannot be used as identifiers in others.
Most keywords in H2 are also reserved (+) or non-reserved (NR) words in the SQL Standard.
Newer versions of H2 may have more keywords than older ones.


H2中的一些关键字是上下文敏感的（CS），这样的关键字在某些地方可以用作标识符，但在其他地方不能用作标识符。
H2 中的大多数关键字也是 SQL 标准中的保留 (+) 或非保留 (NR) 字。
较新版本的 H2 可能比旧版本有更多的关键字。

---
