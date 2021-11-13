## Core Components

This document attempts to give you an overview of the core classes that make up DbUnit. 
Following are the core interfaces or abstract classes: 

| Class | Description |
| ---- | ---- |
| 'IDatabaseConnection' | Interface representing a DbUnit connection to a database. |
| 'IDataSet' | Interface representing a collection of tables. |
| 'DatabaseOperation' | Abstract class representing an operation performed on the database before and after each test. |
| 'ValueComparer' | Interface representing a strategy for comparing values, typically columns. |
| 'VerifyTableDefinition' | Interface representing how to verify a table meets expectations. |

## IDatabaseConnection

The IDatabaseConnection interface represents a DbUnit connection to a database. 

| Class | Description |
| ---- | ---- |
| `DatabaseConnection` | Wraps a JDBC connection. |
| `DatabaseDataSourceConnection` | Wraps a JDBC DataSource. |

## IDataSet

The IDataSet interface represents is a collection of tables. This is the primary abstraction used by DbUnit to manipulate tabular data.

Most commonly used implemetations: 

| Implementation | Description |
| ---- | ---- |
| `FlatXmlDataSet` |  |
| `XmlDataSet` |  |
| `StreamingDataSet` |  |
| `DatabaseDataSet` |  |
| `QueryDataSet` |  |
| `DefaultDataSet` |  |
| `CompositeDataSet` |  |
| `FilteredDataSet` |  |
| `XlsDataSet` |  |
| `ReplacementDataSet` |  |
| `CsvDataSet` |  |

## DatabaseOperation

DatabaseOperation is an abstract class that represents an operation performed on the database before and after each test.

The two most useful operations are REFRESH and CLEAN_INSERT. 
They are the ones you will deal usually with. 
They represent two testing strategies with different benefits and tradeoffs. 

| Operation | Description |
| ---- | ---- |
| `DatabaseOperation.UPDATE` | This operation updates the database from the dataset contents. This operation assumes that table data already exists in the target database and fails if this is not the case. |
| `DatabaseOperation.INSERT` | This operation inserts the dataset contents into the database. This operation assumes that table data does not exist in the target database and fails if this is not the case. To prevent problems with foreign keys, tables must be sequenced appropriately in the dataset. |
| `DatabaseOperation.DELETE` | This operation deletes only the dataset contents from the database. This operation does not delete the entire table contents but only data that are present in the dataset. |
| `DatabaseOperation.DELETE_ALL` | Deletes all rows of tables present in the specified dataset. If the dataset does not contains a particular table, but that table exists in the database, the database table is not affected. Table are truncated in reverse sequence. |
| `DatabaseOperation.TRUNCATE_TABLE` | Truncate tables present in the specified dataset. If the dataset does not contains a particular table, but that table exists in the database, the database table is not affected. Table are truncated in reverse sequence. |
| `DatabaseOperation.REFRESH` | This operation literally refreshes dataset contents into the target database. This means that data of existing rows are updated and non-existing row get inserted. Any rows which exist in the database but not in dataset stay unaffected. This approach is more appropriate for tests that assume other data may exist in the database. if they are correctly written, tests using this strategy can even be performed on a populated database like a copy of a production database. |
| `DatabaseOperation.CLEAN_INSERT` | This composite operation performs a DELETE_ALL operation followed by an INSERT operation. This is the safest approach to ensure that the database is in a known state. This is appropriate for tests that require the database to only contain a specific set of data. |
| `DatabaseOperation.NONE` | Empty operation that does absolutely nothing. |
| `CompositeOperation` | This operation combines multiple operations into a single one. |
| `TransactionOperation` | This operation decorates an operation and executes it within the context of a transaction. |
| `InsertIdentityOperation` | This operation decorates an insert operation and disables the MS SQL Server automatic identifier generation (IDENTITY) during its execution. Use following constants InsertIdentityOperation.INSERT, InsertIdentityOperation.CLEAN_INSERT or InsertIdentityOperation.REFRESH instead of those defined in DatabaseOperation. |

## ValueComparer

ValueComparer is a strategy for comparing values, a data comparison definition.

VerifyTableDefinition directly supports using ValueComparer in the definition..

Refer to Data Comparisons for more information.

### ValueComparers

ValueComparers is a class of static ValueComparer instances. Please create a feature request for new ValueComparer ideas we can add to this class.

## VerifyTableDefinition

VerifyTableDefinition defines a database table to verify (assert on data), specifying include and exclude column filters and optional ValueComparers.

PrepAndExpectedTestCase directly supports using VerifyTableDefinitions in the test definition and execution.

