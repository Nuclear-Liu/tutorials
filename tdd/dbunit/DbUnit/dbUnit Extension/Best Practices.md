## Best Practices

1. Use one database instance per developer
2. Good setup don't need cleanup!
3. Use multiple small datasets
4. Perform setup of stale data once for entire test class or test suite
5. Connection management strategies

## Use one database instance per developer

Testing can be simplified if you can get your database in a known state before a test is run. 
A database should only be used for one test at a time; otherwise the database state cannot be guarantied.

So multiple developers working on the same project should have their own database instance to prevent data corruption. 
This also simplifies database cleanup, as you don't necessarily need needs to revert it to its initial state.

## Good setup don't need cleanup!

You should always avoid creating tests that depends on results of preceding tests; thankfully this is the main purpose of DbUnit.

Don't be afraid to leave your trace after a test; principally if you are using one database instance per developer. 
If you always put your database in a known state before a test execution, you usually don't need to clean it up.. 
This simplifies your tests maintenance and reduces the overhead taken by the cleanup procedure. 
And sometimes, this is very helpful to manually verify the database state after executing a test that fails.

## Use multiple small datasets

Most of your tests do not require the entire database to be re-initialized. 
So, instead of putting your entire database data in one large dataset, try to break it into many smaller chunks.

These chunks could roughly corresponding to logical units, or components. 
This reduces the overhead caused by initializing your database for each test. 
This also facilitates team development since many developers working on different components can modify datasets independently.

For integrated testing, you can still use the CompositeDataSet class to logically combine multiple datasets into a large one at run time.

## Perform setup of stale data once for entire test class or test suite

If several tests are using the same read-only data, this data could be initialized once for an entire test class or test suite. 
You need to be cautious and ensure you never modify this data. 
This can reduce the time required to run your tests but also introduces more risk.

## Connection management strategies

Here are the recommended connection management strategies depending whether you test from a remote client or an in-container strategy:

### Remote client with DatabaseTestCase

You should try to reuse the same connection for the entire test suite to reduce the overhead of creating a new connection for each test. 
Since version 1.1, DatabaseTestCase is closing every connection in setUp() and tearDown(). Override the closeConnection() method with an empty body to modify this behavior.

### In-container with Cactus or JUnitEE

If you use the in-container strategy you should use the DatabaseDataSourceConnection class to access the DataSource you configured for your application server. 
JDBC connections are requested on demand from the DataSource. 
So you can rely on the built-in connection pooling capability of your application server to achieve good performance. 

`IDatabaseConnection connection = new DatabaseDataSourceConnection(new InitialContext(), "jdbc/myDataSource");`

An alternative since version 2.2 is to subclass JndiBasedDBTestCase and specify the JNDI lookup name. 

```text
public class MyJNDIDatabaseTest extends JndiBasedDBTestCase {
   protected String getLookupName(){
      return "jdbc/myDatasource";
   }
   ...
}
```

You may also use JndiDatabaseTester if you can't subclass JndiBasedDBTestCase. 
