# MongoDB

> 适用于价值低的数据，对事务要求不高的场景

## MongoDB 术语

* `database` : 数据库
* `collection` : 数据库表、集合
* `document` : 数据库记录行、文档
* `field` : 数据字段、域
* `index` : 索引
* `嵌入文档` : 通过嵌入文档代替多表连接
* `primary key` : 主键(MongoDB 自动生成将 `_id` 作为主键)

## 版本主要特性

* `1.0` 支持**复制集**和**分片集**
* `2.0` 增强数据库功能
* `3.0` 引入**引擎**概念
* `4.0` 支持**分布式事务**

## 数据模型

MongoDB 的最小存储单位是**文档(document)**（对应关系数据中的**行**）；
数据以 [`BSON`](https://bsonspec.org/) 文档格式存储；

> `BSON(Binary JSON)` 是一种类似 JSON 的文档的二进制编码序列化。
> 支持**内嵌文档对象**和**数组对象**；
> 支持不属于JSON规范的**数据类型的扩展**（如： `Date` `BinData` 类型）。
> 
> `BSON` 可以与二进制交换格式进行比较，如 [`Protocol Buffers`](https://developers.google.com/protocol-buffers/) 。
> `BSON` 比 `Protocol Buffers` 更**无模式**，相对的**在空间效率**劣势（**在序列化数据中的字段名称方面具有开销**）。
> 
> BSON 特征：
> 1. 轻量
> 2. 可遍历
> 3. 高效

### `BSON` 数据类型

| Type                         | Number | Alias                 | Notes                  |
|------------------------------|--------|-----------------------|------------------------|
| `Double`                     | `1`    | "double"              |                        |
| `String`                     | `2`    | "string"              |                        |
| `Object`                     | `3`    | "object"              |                        |
| `Array`                      | `4`    | "array"               |                        |
| `Binary data`                | `5`    | "binData"             |                        |
| `Undefined`                  | `6`    | "undefined"           | Deprecated.            |
| `ObjectId`                   | `7`    | "objectId"            |                        |
| `Boolean`                    | `8`    | "bool"                |                        |
| `Date`                       | `9`    | "date"                |                        |
| `null`                       | `10`   | "null"                |                        |
| `Regular Expression`         | `11`   | "regex"               |                        |
| `DBPointer`                  | `12`   | "dbPointer"           | Deprecated.            |
| `JavaScript`                 | `13`   | "javascript"          |                        |
| `Symbol`                     | `14`   | "symbol"              | Deprecated.            |
| `JavaScript code with scope` | `15`   | "javascriptWithScope" | Deprecated with 4.2.1. |
| `32-bit integer`             | `16`   | "int"                 |                        |
| `Timestamp`                  | `17`   | "timestamp"           |                        |
| `64-bit integer`             | `18`   | "long"                |                        |
| `Decimal128`                 | `19`   | "decimal"             | New in version 3.4.    |
| `Min key`                    | `-1`   | "minKey"              |                        |
| `Max key`                    | `127`  | "maxKey"              |                        |

## [MongoDB 高可用（复制集）]()

## [MongoDB 高可靠（分片）]()

