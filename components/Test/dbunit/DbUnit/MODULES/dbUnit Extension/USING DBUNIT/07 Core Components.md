## Core Components _核心组件_

This document attempts to give you an overview of the core classes that make up DbUnit. 
Following are the core interfaces or abstract classes: 


本文档试图为您提供组成 DbUnit 的核心类的概述。
以下是核心接口或抽象类：

---

| Class | Description |
| ---- | ---- |
| 'IDatabaseConnection' | Interface representing a DbUnit connection to a database. |
| 'IDataSet' | Interface representing a collection of tables. |
| 'DatabaseOperation' | Abstract class representing an operation performed on the database before and after each test. |
| 'ValueComparer' | Interface representing a strategy for comparing values, typically columns. |
| 'VerifyTableDefinition' | Interface representing how to verify a table meets expectations. |


| 类 | 描述 |
| ---- | ---- |
| 'IDatabaseConnection' | 表示与数据库的 DbUnit 连接的接口。 |
| 'IDataSet' | 表示表集合的接口。 |
| 'DatabaseOperation' | 抽象类，表示在每次测试前后对数据库执行的操作。 |
| 'ValueComparer' | 表示比较值（通常为列）的策略的接口。 |
| 'VerifyTableDefinition' | 表示如何验证表是否符合预期的接口。 |

---

## `IDatabaseConnection`

The `IDatabaseConnection` interface represents a DbUnit connection to a database. 


`IDatabaseConnection` 接口表示到数据库的 DbUnit 连接。

---

| Class | Description |
| ---- | ---- |
| `DatabaseConnection` | Wraps a JDBC connection. |
| `DatabaseDataSourceConnection` | Wraps a JDBC DataSource. |


| 类 | 描述 |
| ---- | ---- |
| `DatabaseConnection` | 包装一个 JDBC 连接 |
| `DatabaseDataSourceConnection` | 包装一个 JDBC 数据源。|

---

## `IDataSet`

The `IDataSet` interface represents is a collection of tables. 
This is the primary abstraction used by DbUnit to manipulate tabular data.


`IDataSet` 接口代表的是一个表的集合。
这是 DbUnit 用于操作表格数据的主要抽象。

---

Most commonly used implemetations: 


最常用的含义：

---

| Implementation | Description |
| ---- | ---- |
| `FlatXmlDataSet` | [FlatXmlDataSet]() |
| `XmlDataSet` | [XmlDataSet]() |
| `StreamingDataSet` | [StreamingDataSet]() |
| `DatabaseDataSet` | [DatabaseDataSet]() |
| `QueryDataSet` | [QueryDataSet]() |
| `DefaultDataSet` | [DefaultDataSet]() |
| `CompositeDataSet` | [CompositeDataSet]() |
| `FilteredDataSet` | [FilteredDataSet]() |
| `XlsDataSet` | [XlsDataSet]() |
| `ReplacementDataSet` | [ReplacementDataSet]() |
| `CsvDataSet` | [CsvDataSet]() |

## `DatabaseOperation`

`DatabaseOperation` is an abstract class that represents an operation performed on the database before and after each test.


`DatabaseOperation` 是一个抽象类，表示每次测试前后对数据库执行的操作。

---

The two most useful operations are `REFRESH` and `CLEAN_INSERT`. 
They are the ones you will deal usually with. 
They represent two testing strategies with different benefits and tradeoffs. 


两个最有用的操作是“REFRESH”和“CLEAN_INSERT”。
他们是您通常会与之打交道的人。
它们代表了两种具有不同优势和权衡的测试策略。

---

| Operation | Description |
| ---- | ---- |
| `DatabaseOperation.UPDATE` | This operation updates the database from the dataset contents. This operation assumes that table data already exists in the target database and fails if this is not the case. |
| `DatabaseOperation.INSERT` | This operation inserts the dataset contents into the database. This operation assumes that table data does not exist in the target database and fails if this is not the case. To prevent problems with foreign keys, tables must be sequenced appropriately in the dataset. |
| `DatabaseOperation.DELETE` | This operation deletes only the dataset contents from the database. This operation does not delete the entire table contents but only data that are present in the dataset. |
| `DatabaseOperation.DELETE_ALL` | Deletes all rows of tables present in the specified dataset. If the dataset does not contains a particular table, but that table exists in the database, the database table is not affected. Table are truncated in reverse sequence. |
| `DatabaseOperation.TRUNCATE_TABLE` | Truncate tables present in the specified dataset. If the dataset does not contains a particular table, but that table exists in the database, the database table is not affected. Table are truncated in reverse sequence. |
| `DatabaseOperation.REFRESH` | This operation literally refreshes dataset contents into the target database. This means that data of existing rows are updated and non-existing row get inserted. Any rows which exist in the database but not in dataset stay unaffected. This approach is more appropriate for tests that assume other data may exist in the database. if they are correctly written, tests using this strategy can even be performed on a populated database like a copy of a production database. |
| `DatabaseOperation.CLEAN_INSERT` | This composite operation performs a `DELETE_ALL` operation followed by an `INSERT` operation. This is the safest approach to ensure that the database is in a known state. This is appropriate for tests that require the database to only contain a specific set of data. |
| `DatabaseOperation.NONE` | Empty operation that does absolutely nothing. |
| `CompositeOperation` | This operation combines multiple operations into a single one. |
| `TransactionOperation` | This operation decorates an operation and executes it within the context of a transaction. |
| `InsertIdentityOperation` | This operation decorates an insert operation and disables the MS SQL Server automatic identifier generation (`IDENTITY`) during its execution. Use following constants `InsertIdentityOperation.INSERT`, `InsertIdentityOperation.CLEAN_INSERT` or `InsertIdentityOperation.REFRESH` instead of those defined in `DatabaseOperation`. |


| 操作 | 描述 |
| ---- | ---- |
| `DatabaseOperation.UPDATE` | 此操作从数据集内容更新数据库。此操作假定表数据已存在于目标数据库中，否则将失败。 |
| `DatabaseOperation.INSERT` | 此操作将数据集内容插入到数据库中。此操作假定目标数据库中不存在表数据，否则将失败。为防止外键出现问题，必须对数据集中的表进行适当排序。 |
| `DatabaseOperation.DELETE` | 此操作仅从数据库中删除数据集内容。此操作不会删除整个表内容，而只会删除数据集中存在的数据。 |
| `DatabaseOperation.DELETE_ALL` | 删除指定数据集中存在的所有表行。如果数据集不包含特定表，但该表存在于数据库中，则数据库表不受影响。表格以相反的顺序截断。 |
| `DatabaseOperation.TRUNCATE_TABLE` | 截断指定数据集中存在的表。如果数据集不包含特定表，但该表存在于数据库中，则数据库表不受影响。表格以相反的顺序截断。 |
| `DatabaseOperation.REFRESH` | 此操作实际上是将数据集内容刷新到目标数据库中。这意味着更新现有行的数据并插入不存在的行。任何存在于数据库中但不在数据集中的行不受影响。这种方法更适用于假设数据库中可能存在其他数据的测试。如果编写正确，甚至可以在已填充的数据库（如生产数据库的副本）上执行使用此策略的测试。 |
| `DatabaseOperation.CLEAN_INSERT` | 此复合操作执行 `DELETE_ALL` 操作，然后是 `INSERT` 操作。这是确保数据库处于已知状态的最安全方法。这适用于要求数据库仅包含一组特定数据的测试。 |
| `DatabaseOperation.NONE` | 完全不做任何事情的空操作。 |
| `CompositeOperation` | 此操作将多个操作合并为一个操作。 |
| `TransactionOperation` | 此操作修饰一个操作并在事务的上下文中执行它。 |
| `InsertIdentityOperation` | 此操作修饰插入操作并在其执行期间禁用 MS SQL Server 自动标识符生成 ( `IDENTITY` )。使用以下常量 `InsertIdentityOperation.INSERT`、`InsertIdentityOperation.CLEAN_INSERT` 或 `InsertIdentityOperation.REFRESH` 代替在 `DatabaseOperation` 中定义的常量。 |

---

## `ValueComparer`

`ValueComparer` is a strategy for comparing values, a data comparison definition.


`ValueComparer` 是一种比较值的策略，一种数据比较定义。

---

`VerifyTableDefinition` directly supports using `ValueComparer` in the definition.


`VerifyTableDefinition` 直接支持在定义中使用 `ValueComparer`。

---

Refer to Data Comparisons for more information.


有关详细信息，请参阅数据比较。

---

### `ValueComparer`s

ValueComparers is a class of static `ValueComparer` instances. 
Please create a feature request for new `ValueComparer` ideas we can add to this class.


ValueComparers 是一类静态的 `ValueComparer` 实例。
请为我们可以添加到此类的新 `ValueComparer` 想法创建功能请求。

---

## `VerifyTableDefinition`

`VerifyTableDefinition` defines a database table to verify (assert on data), specifying include and exclude column filters and optional ValueComparers.


`VerifyTableDefinition` 定义了一个要验证的数据库表（对数据进行断言），指定包含和排除列过滤器以及可选的 ValueComparers。

---

`PrepAndExpectedTestCase` directly supports using `VerifyTableDefinitions` in the test definition and execution.


`PrepAndExpectedTestCase` 直接支持在测试定义和执行中使用 `VerifyTableDefinitions` 。

---
