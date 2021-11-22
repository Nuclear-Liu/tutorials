## Configurable Features and Properties

DbUnit does not use System properties anymore since version 2.0. DbUnit use a configuration object, DatabaseConfig, to query and set feature flags and property values for a IDatabaseConnection. It is possible to change DbUnit behaviors, such as using batched statements or not, using the getFeature, setFeature, getProperty, and setProperty methods of DatabaseConfig.

While feature flags are always boolean, property values are arbitrary objects. Note that starting with dbunit 2.4.6 features can also be set using the setProperty method.

The following sample displays the batched statement feature status: 

```text
  String id = DatabaseConfig.FEATURE_BATCHED_STATEMENTS; 
  DatabaseConfig config = connection.getConfig(); 
  if (config.getFeature(id)) 
  { 
    System.out.println("Batched statements is enabled."); 
  } 
  else 
  { 
    System.out.println("Batched statements is disabled."); 
  } 
```

## Constants

Each Feature Flag ID and Property ID has a corresponding constant on DatabaseConfig.

## Feature Flags

### Batched Statements

|  |  |
| ---- | ---- |
| Feature ID | http://www.dbunit.org/features/batchedStatements |
| Default | false |
| Description | Enable or disable usage of JDBC batched statement by DbUnit. |

### Case Sensitive Table Names

|  |  |
| ---- | ---- |
| Feature ID | http://www.dbunit.org/features/caseSensitiveTableNames |
| Default | false |
| Description | Enable or disable case sensitive table names. If enabled, Dbunit handles all table names in a case sensitive way. |

### Qualified Table Names

|  |  |
| ---- | ---- |
| Feature ID | http://www.dbunit.org/features/qualifiedTableNames |
| Default | false |
| Description | Enable or disable multiple schemas support. If enabled, Dbunit access tables with names fully qualified by schema using this format: SCHEMA.TABLE. |
| Note | This feature was not compatible with the escape pattern property until the 2.2.1 release. Since then the two properties can be mixed without problem: each element will be properly escaped. |

### DataType Warning

|  |  |
| ---- | ---- |
| Feature ID | http://www.dbunit.org/features/datatypeWarning |
| Default | true |
| Description | Enable or disable the warning message displayed when DbUnit encounter an unsupported data type. |

### Skip Oracle 10g Recyclebin Tables

|  |  |
| ---- | ---- |
| Feature ID | http://www.dbunit.org/features/skipOracleRecycleBinTables |
| Default | false |
| Description | Enable or disable the processing of oracle recycle bin tables (tables starting with BIN$). Oracle 10g recyle bin tables may break DbUnit's assumption of tables name uniqueness within a schema since these table are case sensitive. Enable this feature for Oracle 10g databases until the bug in the oracle driver is fixed, which incorrectly reports this system tables to DbUnit. |

### Allow Empty Fields

|  |  |
| ---- | ---- |
| Feature ID | http://www.dbunit.org/features/allowEmptyFields |
| Default | false |
| Description | Allow to call INSERT/UPDATE with empty strings (''). |

## Properties

### Escape Patterns

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/escapePattern |
| Default | none |
| Description | Allows schema, table and column names escaping. The property value is an escape pattern where the ? is replaced by the name. For example, the pattern "[?]" is expanded as "[MY_TABLE]" for a table named "MY_TABLE". The most common escape pattern is "\"?\"" which surrounds the table name with quotes (for the above example it would result in "\"MY_TABLE\""). As a fallback if no questionmark is in the given String and its length is one it is used to surround the table name on the left and right side. For example the escape pattern "\"" will have the same effect as the escape pattern "\"?\"".  |
| Note | This property was not compatible with the qualified table names feature until 2.2.1. Since then the two properties can be mixed resulting in each element properly escaped.  |

### Table Type

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/tableType |
| Type | String[] |
| Description | Used to configure the list of table types recognized by DbUnit. See java.sql.DatabaseMetaData.getTables() for possible values. |
| Default | String[]{"TABLE"} |

### DataType Factory

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/datatypeFactory |
| Default | org.dbunit.dataset.datatype.DefaultDataTypeFactory |
| Description | Used to configure the DataType factory. You can replace the default factory to add support for non-standard database vendor data types. The Object must implement org.dbunit.dataset.datatype.IDataTypeFactory. |
| Note | The following factories are currently available: <br/> * org.dbunit.ext.db2.Db2DataTypeFactory <br/> * org.dbunit.ext.h2.H2DataTypeFactory <br/> * org.dbunit.ext.hsqldb.HsqldbDataTypeFactory <br/> * org.dbunit.ext.mckoi.MckoiDataTypeFactory <br/> * org.dbunit.ext.mssql.MsSqlDataTypeFactory <br/> * org.dbunit.ext.mysql.MySqlDataTypeFactory <br/> * org.dbunit.ext.oracle.OracleDataTypeFactory <br/> * org.dbunit.ext.oracle.Oracle10DataTypeFactory <br/> * org.dbunit.ext.postgresql.PostgresqlDataTypeFactory <br/> * org.dbunit.ext.netezza.NetezzaDataTypeFactory <br/> When you want to create your own data type factory you may also want to look at the generic base implementation at org.dbunit.dataset.datatype.DefaultDataTypeFactory |

### Statement Factory

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/statementFactory |
| Default | org.dbunit.database.statement.PreparedStatementFactory |
| Description | Used to configure the statement factory. The Object must implement org.dbunit.database.statement.IStatementFactory.  |

### ResultSetTable Factory

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/resultSetTableFactory |
| Default | org.dbunit.database.CachedResultSetTableFactory |
| Description | Used to configure the ResultSet table factory. The Object must implement org.dbunit.database.IResultSetTableFactory. |

### Primary Keys Filter

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/primaryKeyFilter |
| Default | none |
| Description | Use to override primary keys detection. The Object must implement org.dbunit.dataset.filter.IColumnFilter.  |

### MS SQL Server IDENTITY Column Filter

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/mssql/identityColumnFilter |
| Default | none |
| Description | Use to override IDENTITY column detection. The Object must implement org.dbunit.dataset.filter.IColumnFilter. |

### Batch Size

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/batchSize |
| Default | 100 |
| Description | Integer object giving the size of batch updates. |

### Fetch Size

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/fetchSize |
| Default | 100 |
| Description | Integer object giving the statement fetch size for loading data into a result set table. |

### Metadata Handler

|  |  |
| ---- | ---- |
| Property ID | http://www.dbunit.org/properties/metadataHandler |
| Default | org.dbunit.database.DefaultMetadataHandler |
| Description | Used to configure the handler used to control database metadata related methods. The Object must implement org.dbunit.database.IMetadataHandler.  |
| Note | The following RDBMS specific handlers are currently available: <br/> * org.dbunit.ext.db2.Db2MetadataHandler <br/> * org.dbunit.ext.mysql.MySqlMetadataHandler <br/> * org.dbunit.ext.netezza.NetezzaMetadataHandler <br/> For all others the default handler should do the job: org.dbunit.database.DefaultMetadataHandler |
