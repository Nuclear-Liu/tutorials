## Getting Started

1. Database setup with a DBTestCase subclass
2. Database setup with your own TestCase subclass
3. Database setup with no parent class
4. Database data verification
5. Data File Loader
6. DbUnit Ant task and Canoo WebTest

## Database setup with a DBTestCase subclass

### Step 1: Create your dataset file

Your test need some data to work with. 
This means you must create a dataset. In most situations you will work with xml datasets. 
You can manually create a flat XML dataset from scratch or create one by [exporting]() some data from your database.

### Step 2: Extend a DBTestCase class

Now you need to create a test class. 
One way to use Dbunit is to have the test class extend the DBTestCase class. 
DBTestCase extends the JUnit TestCase class. 
A template method is required to be implemented: getDataSet() to return the dataset you created in step 1. DBTestCase relies on a IDatabaseTester to do its work, the default configuration uses PropertiesBasedJdbcDatabaseTester, it locates configuration for the DriverManager within the Sytem properties. 
The simplest way to configure it would be in the constructor of your test class. 
You may modify this behavior by overriding getDatabaseTester(), using one of the other 3 provided IDatabaseTester implementations or your own.

You may also use a subclass of DBTestCase, such as one of these: 

|  |  |
| ---- | ---- |
| `JdbcBasedDBTestCase` | uses a DriverManager to create connections (with the aid of a JdbcDatabaseTester). |
| `DataSourceBasedDBTestCase` | uses a javax.sql.DataSource to create connections (with the aid of a DataSourceDatabaseTester). |
| `JndiBasedDBTestCase` | uses a javax.sql.DataSource located through JNDI (with the aid of a JndiDatabaseTester). |
| `DefaultPrepAndExpectedTestCase` | uses a configurable IDatabaseTester (allowing any connection type) providing a turn-key test setup and verification process in one, with clear separation of prep and expected datasets. |

Refer to [dbUnit Test Cases]() page for more details.

The following is a sample implementation that returns a connection to a Hypersonic database and a xml dataset: 

```java
public class SampleTest extends DBTestCase
{
    public SampleTest(String name)
    {
        super( name );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:sample" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );
	// System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
    }

    protected IDataSet getDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("dataset.xml"));
    }
}
```

### Step 3: (Optional) Implement getSetUpOperation() and getTearDownOperation() methods

By default, Dbunit performs a [CLEAN_INSERT]() operation before executing each test and performs no cleanup operation afterward. 
You can modify this behavior by overriding getSetUpOperation() and getTearDownOperation().

The following example demonstrates how you can easily override the operation executed before or after your test. 

```
public class SampleTest extends DBTestCase
{
    ...
    protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.NONE;
    }
    ...
}
```

### Step 4: (Optional) Override method setUpDatabaseConfig(DatabaseConfig config)

Use this to change some configuration settings of the dbunit DatabaseConfig.

The following example demonstrates how you can easily override this method: 

```
public class SampleTest extends DBTestCase
{
    ...
    /**
     * Override method to set custom properties/features
     */
    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
    }
    ...
}
```

### Step 5: Implement your testXxx() methods

Implement your test methods as you normally would with JUnit. 
Your database is now initialized before and cleaned-up after each test methods according to what you did in previous steps.

## Database setup with your own TestCase subclass

In order to use Dbunit you are not required to extend the DBTestCase class. 
You can override the standard JUnit setUp() method and execute the desired operation on your database. 
Do something similar in teardown() if you need to perform clean-up.

For example: 

```java
public class SampleTest extends TestCase
{
    public SampleTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        // initialize your database connection here
        IDatabaseConnection connection = null;
        // ...

        // initialize your dataset here
        IDataSet dataSet = null;
        // ...

        try1
        {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        }
        finally
        {
            connection.close();
        }
    }
    ...
}
```

Since version 2.2 you may use the new IDatabaseTester to accomplish the same feat. 
As explained in the previous topic, DBTestCase uses a IDatabaseTester internally to do its work; your test class may also use this feature to manipulate DataSets. 
Currently there are 4 convenient implementations:

|  |  |
| ---- | ---- |
| `JdbcDatabaseTester` | uses a DriverManager to create connections. |
| `PropertiesBasedJdbcDatabaseTester` | also uses DriverManager, but the configuration is taken from system properties. This is the default implementation used by DBTestCase. |
| `DataSourceDatabaseTester` | uses a javax.sql.DataSource to create connections. |
| `JndiDatabaseTester` | uses a javax.sql.DataSource located through JNDI. |

You may also provide your own IDatabaseTester implementation. 
It is recommended to use AbstractDatabaseTester as a starting point.

Example: 

```java
public class SampleTest extends TestCase
{
    private IDatabaseTester databaseTester;

    public SampleTest(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        databaseTester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
            "jdbc:hsqldb:sample", "sa", "");

        // initialize your dataset here
        IDataSet dataSet = null;
        // ...

        databaseTester.setDataSet( dataSet );
	// will call default setUpOperation
        databaseTester.onSetup();
    }

    protected void tearDown() throws Exception
    {
	// will call default tearDownOperation
        databaseTester.onTearDown();
    }
    ...
}
```

## Database setup with no parent class

In order to use Dbunit you are not required to extend any classes. 
Simply configure an instance of a DBTestCase subclass, whether directly instantiated or dependency injected in test classes.

For example, using PrepAndExpectedTestCase: (also see `TestCases` -> `PrepAndExpectedTestCase` and `DefaultPrepAndExpectedTestCase` JavaDoc). 

```java
public class SampleTest
{
    private PrepAndExpectedTestCase tc; // injected or instantiated, already configured

    @Test
    public void testExample() throws Exception
    {
        final String[] prepDataFiles = {}; // define prep files
        final String[] expectedDataFiles = {}; // define expected files
        final VerifyTableDefinition[] tables = {}; // define tables to verify
        final PrepAndExpectedTestCaseSteps testSteps = () -> {
            // execute test steps that exercise production code
            // e.g. call repository, call REST service

            // assert responses or other values

            // after this method exits, dbUnit will:
            //  * verify configured tables
            //  * cleanup tables as configured

            return null; // or an object for use/assert outside the Steps
        };

        Object result = tc.runTest(tables, prepDataFiles, expectedDataFiles, testSteps);
    }
}
```

## Database data verification

Dbunit provides support for verifying whether two tables or datasets contain identical data. 
The Assertion class has many methods for verifying if your database contains the expected data during test cases execution. 
Here are some of them: 

```java
public class Assertion
{
    public static void assertEquals(ITable expected, ITable actual)
    public static void assertEquals(IDataSet expected, IDataSet actual)
    public static void assertWithValueComparer(IDataSet expectedDataSet, IDataSet actualDataSet,
        ValueComparer defaultValueComparer, Map<String, Map<String, ValueComparer>> tableColumnValueComparers)
    public static void assertWithValueComparer(ITable expectedTable, ITable actualTable,
        ValueComparer defaultValueComparer, Map<String, ValueComparer> columnValueComparers)
}
```

### Sample

The following sample shows how to compare a database table snapshot against a flat XML table using equality comparison. 

```java
public class SampleTest extends DBTestCase
{
    public SampleTest(String name)
    {
        super(name);
    }

    // Implements required setup methods here
    ...

    public void testMe() throws Exception
    {
        // Execute the tested code that modify the database here
        ...


        // Fetch database data after executing your code
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("TABLE_NAME");

        // Load expected data from an XML dataset
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("expectedDataSet.xml"));
        ITable expectedTable = expectedDataSet.getTable("TABLE_NAME");

        // Assert actual database table match expected table
        Assertion.assertEquals(expectedTable, actualTable);
    }
}
```

The actual dataset is a database snapshot you want to verify against an expected dataset. 
As its name imply, the expected dataset contains the expectation values.

The expected dataset must be different from the one you have used to setup your database. 
Therefore you need two datasets to do that; one to setup your database before a test and another to provide the expected data during the test.

### Using a query to take the database snapshot

You can also verify if the result of a query match an expected set of data. 
The query can be used to select only a subset of a table or even join multiple tables together.

`ITable actualJoinData = getConnection().createQueryTable("RESULT_NAME", "SELECT * FROM TABLE1, TABLE2 WHERE ..."); `

### Ignoring some columns in comparison

Sometimes this is desirable to ignore some columns to perform the comparison; particularly for primary keys, date or time columns having values generated by the code under test. 
One way to do this is to omit to declare unwanted columns in your expected table. 
You can then filter the actual database table to only expose the expected table columns.

The following code snippet shows you how to filter the actual table. 
To works, the actual table MUST contain at least ALL the columns from the expected table. 
Extra columns can exist in the actual table but not in the expected one. 

```
    ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actual, 
            expected.getTableMetaData().getColumns());
    Assertion.assertEquals(expected, filteredTable);
```

A major limitation of this technique is that you cannot use a DTD with your expected flat XML dataset. 
With a DTD you need to filter columns from both the expected and the actual table. See the FAQ about [excluding some table columns at runtime](). 

### Row ordering

By default, database table snapshot taken by DbUnit are sorted by primary keys. 
If a table does not have a primary key or the primary key is automatically generated by your database, the rows ordering is not predictable and `assertEquals` will fail.

You must order your database snapshot manually by using `IDatabaseConnection.createQueryTable` with an "ORDER BY" clause. 
Or you can use the `SortedTable` decorator class like this: 

`Assertion.assertEquals(new SortedTable(expected), new SortedTable(actual, expected.getTableMetaData()));`

Note that the SortedTable uses the string value of each column for doing the sort by default. 
So if you are sorting a numeric column you notice that the sort order is like 1, 10, 11, 12, 2, 3, 4. 
If you want to use the columns datatype for sorting (to get the columns like 1, 2, 3, 4, 10, 11, 12) you can do this as follows: 

```
          SortedTable sortedTable1 = new SortedTable(table1, new String[]{"COLUMN1"});
          sortedTable1.setUseComparable(true); // must be invoked immediately after the constructor
          SortedTable sortedTable2 = new SortedTable(table2, new String[]{"COLUMN1"});
          sortedTable2.setUseComparable(true); // must be invoked immediately after the constructor
          Assertion.assertEquals(sortedTable1, sortedTable2);
```

_The reason why the parameter is currently not in the constructor is that the number of constructors needed for SortedTable would increase from 4 to 8 which is a lot. 
Discussion should go on about this feature on how to implement it the best way in the future._ 

### Assert and collect the differences

By default, dbunit immediately fails when the first data difference was found. 
Starting with dbunit 2.4 it is possible to register a custom `FailureHandler` which lets users specify which kinds of exceptions to be thrown and how to handle the occurrences of data differences. 
Using the `DiffCollectingFailureHandler` you can avoid an exception to be thrown on a data mismatch so that you can evaluate all results of the data comparison afterwards. 

```
IDataSet dataSet = getDataSet();
DiffCollectingFailureHandler myHandler = new DiffCollectingFailureHandler();
//invoke the assertion with the custom handler
assertion.assertEquals(dataSet.getTable("TEST_TABLE"),
                       dataSet.getTable("TEST_TABLE_WITH_WRONG_VALUE"),
                       myHandler);
// Evaluate the results and throw an failure if you wish
List diffList = myHandler.getDiffList();
Difference diff = (Difference)diffList.get(0);
...
```

## Data File Loader

Nearly all tests need to load data from one or more files, particularly for prep or expected data. 
dbUnit has a set of data file loader utility helper classes to load data sets from files on the classpath. 
The available loaders are in package org.dbunit.util.fileloader. A simple usage example: 

```
DataFileLoader loader = new FlatXmlDataFileLoader();
IDataSet ds = loader.load("/the/package/prepData.xml");
      
```

Note the constructors for the various DataFileLoaders accept replacement object and replacement substring maps as used with ReplacementDataSet.

Refer to the DataFileLoader JavaDoc for further details. 

## DbUnit Ant task and Canoo WebTest

By Eric Pugh

With Dbunit Ant tasks, Dbunit makes it much easier to run Canoo WebTest scripts for database centric applications. 
WebTest is a tool to simulate a user's browser clicking through the pages on a web site. 
It allows you to create a series of Ant based tests for your website. 
In fact, this can be used to perform User Acceptance tests for websites built using non Java technologies like ColdFusion or ASP! 
This document walks you through a suggested format for storing tests.

### Step 1: Create your dataset file

Your first step is to create your dataset file that you want to load into your database before running your WebTest script. 
Use one of the various methods described above. Put the various datasets you need in a /data directory.

### Step 2: Create your Ant build.xml file

A suggested setup is to have a single build.xml file that is the entry point for all your tests. 
This would include a couple targets like:

1. `test`: Runs all the testSuites that you have created
2. `test:single`: Runs a single test in a specific testSuite
3. `test:suite`: Runs all the tests for a specific testSuite 

### Step 3: Create your various Test Suites

Once you have your build.xml file set up, you can now call the various TestSuites. 
Create a separate TestSuiteXXX.xml for the various modules that you would like to test. 
In your TestSuiteXXX.xml, you should have your default target testSuite call all the testcases you have definied: 

```xml
      <target name="testSuite">

        <antcall target="unsubscribeEmailAddressWithEmail"/>
        <antcall target="unsubscribeEmailAddressWithEmailID"/>
        <antcall target="unsubscribeEmailAddressWithNewEmailAddress"/>

        <antcall target="subscribeEmailAddressWithOptedOutEmail"/>
        <antcall target="subscribeEmailAddressWithNewEmailAddress"/>
        <antcall target="subscribeEmailAddressWithInvalidEmailAddress"/>

      </target>
```

This way you can either run all the test's in your Test Suite, or just run a specific one, all from build.xml!

### Step 4: Create your various Tests

Now you need to write your various testcases. 
For more information on WebTest, please refer to the WebTest home page. 
If you have find you are duplicating pieces of XML, then place them in a /includes directory. 
If you have a single set of properties, then load them as part of build.xml by specifing them in your build.properties file. 
If you have multiple databases you need to connect to, then declare your sql connection properties in a TestSuiteXXX.properties file that you load on a per suite basis. 
In this example, we are using doing a clean insert into the database, and using the MSSQL_CLEAN_INSERT instead of CLEAN_INSERT because of the requirement to do identity column inserts. 

```xml
      <target name="subscribeEmailAddressWithOptedOutEmail">
        <dbunit
            driver="${sql.jdbcdriver}"
            url="${sql.url}"
            userid="${sql.username}"
            password="${sql.password}">
                <operation type="MSSQL_CLEAN_INSERT"
                      src="data/subscribeEmailAddressWithOptedOutEmail.xml"
                format="flat"/>
        </dbunit>
        <testSpec name="subscribeEmailAddressWithOptedOutEmail">
          &amp;sharedConfiguration;
          <steps>
            <invoke stepid="main page"
              url="/edm/subscribe.asp?e=subscribeEmailAddressWithOptedOutEmail@test.com"
              save="subscribeEmailAddressWithNewEmailAddress"/>
            <verifytext stepid="Make sure we received the success message"
              text="You have been subscribed to the mailing list"/>

          </steps>
        </testSpec>
      </target>
```

## Sample Directory Layout

When you are done, you will have a series of files that look like this: 

```text
      \root\
        build.xml
        build.properties
        TestSuiteEDM.xml
        TestSuiteEDM.properties
      \root\data\
        subscribeEmailAddressWithOptedOutEmail.xml
      \root\includes\
        sharedConfiguration.xml
```
