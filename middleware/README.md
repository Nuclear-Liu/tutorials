# Middleware


## [redis](./src/doc/redis/index.md)


* RDB: Redis Database

* AOF: Append Only File

## CAP

CAP 理论的核心是：一个分布式系统不可能同时满足**一致性**、**可用性**和**分区容错性**；最多只能同时满足两个。

* `C`(Consistency) : 一致性

* `A`(Availability) : 可用性

* `P`(Partition Tolerance) : 分区容错性

根据 CAP 原理，分为满足： 
* `CA` : 

    `RDBMS`

    > 单机 MySQL 数据库一般: `6000QPS` ~ `7000QPS`

* `CP` : 

    `MongoDB` `HBase` `Redis`

* `AP` : 

    大多数网站架构的选择
