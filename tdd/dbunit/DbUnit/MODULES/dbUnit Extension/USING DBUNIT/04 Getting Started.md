## Getting Started _入门_

1. Database setup with a DBTestCase subclass
2. Database setup with your own `TestCase` subclass
3. Database setup with no parent class
4. Database data verification
5. Data File Loader
6. DbUnit Ant task and Canoo WebTest


1. 使用 DBTestCase 子类设置数据库
2. 使用您自己的 `TestCase` 子类设置数据库
3. 没有父类的数据库设置
4. 数据库数据校验
5. 数据文件加载器
6. DbUnit Ant 任务和 Canoo WebTest

---

## Database setup with a `DBTestCase` subclass _使用 `DBTestCase` 子类设置数据库_

### Step 1: Create your dataset file _创建数据集文件_

Your test need some data to work with. 
This means you must create a dataset. 
In most situations you will work with xml datasets. 
You can manually create a flat XML dataset from scratch or create one by [exporting]() some data from your database.


您的测试需要使用一些数据。
这意味着您必须创建一个数据集。
在大多数情况下，您将使用 xml 数据集。
您可以从头开始手动创建平面 XML 数据集，也可以通过 [exporting]() 数据库中的一些数据来创建一个。

---

### Step 2: Extend a `DBTestCase` class _扩展 `DBTestCase` 类_

Now you need to create a test class. 
One way to use Dbunit is to have the test class extend the `DBTestCase` class. 
`DBTestCase` extends the JUnit `TestCase` class. 
A template method is required to be implemented: `getDataSet()` to return the dataset you created in step 1. 
`DBTestCase` relies on a `IDatabaseTester` to do its work, the default configuration uses `PropertiesBasedJdbcDatabaseTester`, it locates configuration for the `DriverManager` within the Sytem properties. 
The simplest way to configure it would be in the constructor of your test class. 
You may modify this behavior by overriding `getDatabaseTester()`, using one of the other 3 provided `IDatabaseTester` implementations or your own.


现在您需要创建一个测试类。
使用 Dbunit 的一种方法是让测试类扩展 `DBTestCase` 类。
`DBTestCase` 继承了 JUnit `TestCase` 类。
需要实现一个模板方法：`getDataSet()` 以返回您在步骤 1 中创建的数据集。
`DBTestCase` 依赖于 `IDatabaseTester` 来完成它的工作，默认配置使用 `PropertiesBasedJdbcDatabaseTester`，它在系统属性中定位 `DriverManager` 的配置。
配置它的最简单方法是在测试类的构造函数中。
您可以通过覆盖 `getDatabaseTester()`、使用其他 3 个提供的 `IDatabaseTester` 实现之一或您自己的实现来修改此行为。

---

You may also use a subclass of `DBTestCase`, such as one of these: 


您还可以使用 `DBTestCase` 的子类，例如以下之一：

---

| Class | Description |
| ---- | ---- |
| `JdbcBasedDBTestCase` | uses a `DriverManager` to create connections (with the aid of a `JdbcDatabaseTester` ). |
| `DataSourceBasedDBTestCase` | uses a `javax.sql.DataSource` to create connections (with the aid of a `DataSourceDatabaseTester`). |
| `JndiBasedDBTestCase` | uses a `javax.sql.DataSource` located through `JNDI` (with the aid of a `JndiDatabaseTester`). |
| `DefaultPrepAndExpectedTestCase` | uses a configurable `IDatabaseTester` (allowing any connection type) providing a turn-key test setup and verification process in one, with clear separation of prep and expected datasets. |


| Class | Description |
| ---- | ---- |
| `JdbcBasedDBTestCase` | 使用 `DriverManager` 创建连接（借助 `JdbcDatabaseTester` ）。 |
| `DataSourceBasedDBTestCase` | 使用 `javax.sql.DataSource` 创建连接（借助 `DataSourceDatabaseTester`）。 |
| `JndiBasedDBTestCase` | 使用通过`JNDI` 定位的`javax.sql.DataSource`（借助`JndiDatabaseTester`）。 |
| `DefaultPrepAndExpectedTestCase` | 使用可配置的 `IDatabaseTester` （允许任何连接类型），将交钥匙测试设置和验证过程合二为一，明确分离准备和预期数据集。 |

---

Refer to [dbUnit Test Cases]() page for more details.


有关更多详细信息，请参阅 [dbUnit 测试用例]() 页面。

---

The following is a sample implementation that returns a connection to a Hypersonic database and a xml dataset: 


以下是返回到 Hypersonic 数据库和 xml 数据集的连接的示例实现：

---

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

### Step 3: (Optional) Implement `getSetUpOperation()` and `getTearDownOperation()` methods _（可选）实现 `getSetUpOperation()` 和 `getTearDownOperation()` 方法_

By default, Dbunit performs a [CLEAN_INSERT]() operation before executing each test and performs no cleanup operation afterward. 
You can modify this behavior by overriding `getSetUpOperation()` and `getTearDownOperation()`.


默认情况下，Dbunit 在执行每个测试之前执行 [CLEAN_INSERT]() 操作，之后不执行清理操作。
您可以通过覆盖 `getSetUpOperation()` 和 `getTearDownOperation()` 来修改此行为。

---

The following example demonstrates how you can easily override the operation executed before or after your test. 


以下示例演示了如何轻松覆盖在测试之前或之后执行的操作。

---

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

### Step 4: (Optional) Override method `setUpDatabaseConfig(DatabaseConfig config)` _（可选）覆盖方法 `setUpDatabaseConfig(DatabaseConfig config)`_

Use this to change some configuration settings of the dbunit `DatabaseConfig`.


使用它来更改 dbunit `DatabaseConfig` 的一些配置设置。

---

The following example demonstrates how you can easily override this method: 


以下示例演示了如何轻松覆盖此方法：

---

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

### Step 5: Implement your `testXxx()` methods _实现你的 `testXxx()` 方法_

Implement your test methods as you normally would with JUnit. 
Your database is now initialized before and cleaned-up after each test methods according to what you did in previous steps.


像通常使用 JUnit 一样实现您的测试方法。
您的数据库现在根据您在前面步骤中所做的操作在每个测试方法之前初始化并在每个测试方法之后进行清理。

---

## Database setup with your own TestCase subclass _使用您自己的 TestCase 子类设置数据库_

In order to use Dbunit you are not required to extend the `DBTestCase` class. 
You can override the standard JUnit `setUp()` method and execute the desired operation on your database. 
Do something similar in `teardown()` if you need to perform clean-up.


为了使用 Dbunit，你不需要扩展 `DBTestCase` 类。
您可以覆盖标准的 JUnit `setUp()` 方法并在您的数据库上执行所需的操作。
如果您需要执行清理，请在 `teardown()` 中执行类似的操作。

---

For example: 


例如：

---

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
    // ...
}
```

Since version 2.2 you may use the new `IDatabaseTester` to accomplish the same feat. 
As explained in the previous topic, `DBTestCase` uses a `IDatabaseTester` internally to do its work; your test class may also use this feature to manipulate DataSets. 
Currently there are 4 convenient implementations:


从 2.2 版开始，您可以使用新的“IDatabaseTester”来完成相同的壮举。
如上一主题所述， `DBTestCase` 在内部使用 `IDatabaseTester` 来完成其工作； 您的测试类也可以使用此功能来操作数据集。
目前有4种方便的实现：

---

| Class | Description |
| ---- | ---- |
| `JdbcDatabaseTester` | uses a `DriverManager` to create connections. |
| `PropertiesBasedJdbcDatabaseTester` | also uses `DriverManager`, but the configuration is taken from system properties. This is the default implementation used by `DBTestCase`. |
| `DataSourceDatabaseTester` | uses a `javax.sql.DataSource` to create connections. |
| `JndiDatabaseTester` | uses a `javax.sql.DataSource` located through `JNDI`. |


| Class | Description |
| ---- | ---- |
| `JdbcDatabaseTester` | 使用 `DriverManager` 来创建连接。 |
| `PropertiesBasedJdbcDatabaseTester` | 也使用`DriverManager`，但配置取自系统属性。这是`DBTestCase` 使用的默认实现。 |
| `DataSourceDatabaseTester` | 使用 `javax.sql.DataSource` 来创建连接。 |
| `JndiDatabaseTester` | 使用通过`JNDI` 定位的`javax.sql.DataSource`。 |

---

You may also provide your own `IDatabaseTester` implementation. 
It is recommended to use `AbstractDatabaseTester` as a starting point.


你也可以提供你自己的`IDatabaseTester` 实现。
建议使用 `AbstractDatabaseTester` 作为起点。

---

Example: 


示例：

---

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
    // ...
}
```

## Database setup with no parent class _没有父类的数据库设置_

In order to use Dbunit you are not required to extend any classes. 
Simply configure an instance of a `DBTestCase` subclass, whether directly instantiated or dependency injected in test classes.


为了使用 Dbunit，您不需要扩展任何类。
只需配置一个 `DBTestCase` 子类的实例，无论是直接实例化还是在测试类中注入依赖。

---

For example, using `PrepAndExpectedTestCase`: (also see `TestCases` -> `PrepAndExpectedTestCase` and `DefaultPrepAndExpectedTestCase` JavaDoc). 


例如，使用 `PrepAndExpectedTestCase` ：（另见 `TestCases` -> `PrepAndExpectedTestCase` 和 `DefaultPrepAndExpectedTestCase` JavaDoc）。

---

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

## Database data verification _数据库数据校验_

Dbunit provides support for verifying whether two tables or datasets contain identical data. 
The `Assertion` class has many methods for verifying if your database contains the expected data during test cases execution. 
Here are some of them: 


Dbunit 支持验证两个表或数据集是否包含相同的数据。
`Assertion` 类有许多方法可以在测试用例执行期间验证您的数据库是否包含预期数据。
这里是其中的一些：

---

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

### Sample _案例_

The following sample shows how to compare a database table snapshot against a flat XML table using equality comparison. 


以下示例显示如何使用相等比较将数据库表快照与平面 XML 表进行比较。

---

```java
public class SampleTest extends DBTestCase
{
    public SampleTest(String name)
    {
        super(name);
    }

    // Implements required setup methods here
    // ...

    public void testMe() throws Exception
    {
        // Execute the tested code that modify the database here
        // ...


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


实际数据集是您要针对预期数据集进行验证的数据库快照。
顾名思义，预期数据集包含预期值。

---

The expected dataset must be different from the one you have used to setup your database. 
Therefore you need two datasets to do that; one to setup your database before a test and another to provide the expected data during the test.


预期的数据集必须与您用来设置数据库的数据集不同。
因此，您需要两个数据集才能做到这一点；一个用于在测试前设置数据库，另一个用于在测试期间提供预期数据。

---

### Using a query to take the database snapshot _使用查询获取数据库快照_

You can also verify if the result of a query match an expected set of data. 
The query can be used to select only a subset of a table or even join multiple tables together.


您还可以验证查询结果是否与预期的数据集匹配。
该查询可用于仅选择表的一个子集，甚至可以将多个表连接在一起。

---

`ITable actualJoinData = getConnection().createQueryTable("RESULT_NAME", "SELECT * FROM TABLE1, TABLE2 WHERE ..."); `

### Ignoring some columns in comparison _相比之下忽略一些列_

Sometimes this is desirable to ignore some columns to perform the comparison; particularly for primary keys, date or time columns having values generated by the code under test. 
One way to do this is to omit to declare unwanted columns in your expected table. 
You can then filter the actual database table to only expose the expected table columns.


有时需要忽略某些列来执行比较；特别是对于具有由被测代码生成的值的主键、日期或时间列。
一种方法是省略在预期表中声明不需要的列。
然后，您可以过滤实际的数据库表以仅公开预期的表列。

---

The following code snippet shows you how to filter the actual table. 
To works, the actual table `MUST` contain at least `ALL` the columns from the expected table. 
Extra columns can exist in the actual table but not in the expected one. 


以下代码片段向您展示了如何过滤实际表。
为了工作，实际表 `MUST` 至少包含来自预期表的 `ALL` 列。
额外的列可以存在于实际表中，但不存在于预期的表中。

---

```
    ITable filteredTable = DefaultColumnFilter.includedColumnsTable(actual, 
            expected.getTableMetaData().getColumns());
    Assertion.assertEquals(expected, filteredTable);
```

A major limitation of this technique is that you cannot use a DTD with your expected flat XML dataset. 
With a DTD you need to filter columns from both the expected and the actual table. 
See the FAQ about [excluding some table columns at runtime](). 


这种技术的一个主要限制是不能将 DTD 与预期的平面 XML 数据集一起使用。
使用 DTD，您需要从预期表和实际表中过滤列。
请参阅有关 [在运行时排除某些表列]() 的常见问题解答。

---

### Row ordering _行排序_

By default, database table snapshot taken by DbUnit are sorted by primary keys. 
If a table does not have a primary key or the primary key is automatically generated by your database, the rows ordering is not predictable and `assertEquals` will fail.


默认情况下，DbUnit 抓取的数据库表快照按主键排序。
如果表没有主键或主键是由数据库自动生成的，则行排序是不可预测的，并且 `assertEquals` 将失败。

---

You must order your database snapshot manually by using `IDatabaseConnection.createQueryTable` with an "`ORDER BY`" clause. 
Or you can use the `SortedTable` decorator class like this: 


您必须使用带有 “`ORDER BY`” 子句的 `IDatabaseConnection.createQueryTable` 手动订购数据库快照。
或者你可以像这样使用 `SortedTable` 装饰器类：

---

`Assertion.assertEquals(new SortedTable(expected), new SortedTable(actual, expected.getTableMetaData()));`

Note that the `SortedTable` uses the string value of each column for doing the sort by default. 
So if you are sorting a numeric column you notice that the sort order is like 1, 10, 11, 12, 2, 3, 4. 
If you want to use the columns datatype for sorting (to get the columns like 1, 2, 3, 4, 10, 11, 12) you can do this as follows: 


请注意， `SortedTable` 默认使用每列的字符串值进行排序。
因此，如果您对数字列进行排序，您会注意到排序顺序类似于 1、10、11、12、2、3、4。
如果您想使用列数据类型进行排序（以获得 1、2、3、4、10、11、12 等列），您可以按如下方式执行此操作：

---

```
SortedTable sortedTable1 = new SortedTable(table1, new String[]{"COLUMN1"});
sortedTable1.setUseComparable(true); // must be invoked immediately after the constructor
SortedTable sortedTable2 = new SortedTable(table2, new String[]{"COLUMN1"});
sortedTable2.setUseComparable(true); // must be invoked immediately after the constructor
Assertion.assertEquals(sortedTable1, sortedTable2);
```

_The reason why the parameter is currently not in the constructor is that the number of constructors needed for `SortedTable` would increase from 4 to 8 which is a lot. 
Discussion should go on about this feature on how to implement it the best way in the future._ 


_参数目前不在构造函数中的原因是`SortedTable`所需的构造函数数量将从4个增加到8个，这是很多的。
应该继续讨论此功能，以了解将来如何以最佳方式实现它。_

---

### Assert and collect the differences _断言和收集差异_

By default, dbunit immediately fails when the first data difference was found. 
Starting with dbunit 2.4 it is possible to register a custom `FailureHandler` which lets users specify which kinds of exceptions to be thrown and how to handle the occurrences of data differences. 
Using the `DiffCollectingFailureHandler` you can avoid an exception to be thrown on a data mismatch so that you can evaluate all results of the data comparison afterwards. 


默认情况下，dbunit 在发现第一个数据差异时立即失败。
从 dbunit 2.4 开始，可以注册一个自定义的 `FailureHandler` ，它允许用户指定要抛出的异常类型以及如何处理出现的数据差异。
使用 `DiffCollectingFailureHandler` 可以避免在数据不匹配时抛出异常，以便您可以在之后评估数据比较的所有结果。

---

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

## Data File Loader _加载数据文件_

Nearly all tests need to load data from one or more files, particularly for prep or expected data. 
dbUnit has a set of data file loader utility helper classes to load data sets from files on the classpath. 
The available loaders are in package `org.dbunit.util.fileloader`. 
A simple usage example:


几乎所有测试都需要从一个或多个文件加载数据，尤其是准备或预期数据。
dbUnit 有一组数据文件加载器实用程序助手类，用于从类路径上的文件加载数据集。
可用的加载器位于 `org.dbunit.util.fileloader` 包中。
一个简单的使用示例：

---

```
DataFileLoader loader = new FlatXmlDataFileLoader();
IDataSet ds = loader.load("/the/package/prepData.xml");
      
```

Note the constructors for the various `DataFileLoaders` accept replacement object and replacement substring maps as used with `ReplacementDataSet`.


请注意，各种 `DataFileLoaders` 的构造函数接受与 `ReplacementDataSet` 一起使用的替换对象和替换子字符串映射。

---

Refer to the `DataFileLoader` JavaDoc for further details. 


有关更多详细信息，请参阅 `DataFileLoader` JavaDoc。

---

## DbUnit Ant task and Canoo WebTest _DbUnit Ant 任务和 Canoo WebTest_

By Eric Pugh

With Dbunit Ant tasks, Dbunit makes it much easier to run Canoo WebTest scripts for database centric applications. 
WebTest is a tool to simulate a user's browser clicking through the pages on a web site. 
It allows you to create a series of Ant based tests for your website. 
In fact, this can be used to perform User Acceptance tests for websites built using non Java technologies like ColdFusion or ASP! 
This document walks you through a suggested format for storing tests.


借助 Dbunit Ant 任务，Dbunit 可以更轻松地为以数据库为中心的应用程序运行 Canoo WebTest 脚本。
WebTest 是一种模拟用户浏览器点击网站页面的工具。
它允许您为您的网站创建一系列基于 Ant 的测试。
事实上，这可用于对使用非 Java 技术（如 ColdFusion 或 ASP）构建的网站执行用户验收测试！
本文档将引导您了解用于存储测试的建议格式。

---

### Step 1: Create your dataset file _创建数据集文件_

Your first step is to create your dataset file that you want to load into your database before running your WebTest script. 
Use one of the various methods described above. 
Put the various datasets you need in a `/data` directory.


第一步是在运行 WebTest 脚本之前创建要加载到数据库中的数据集文件。
使用上述各种方法之一。
将您需要的各种数据集放在一个 `/data` 目录中。

---

### Step 2: Create your Ant `build.xml` file _创建您的 Ant `build.xml` 文件_

A suggested setup is to have a single `build.xml` file that is the entry point for all your tests. 
This would include a couple targets like:


建议的设置是有一个单一的 `build.xml` 文件，它是所有测试的入口点。
这将包括几个目标，例如：

---

1. `test`: Runs all the testSuites that you have created
2. `test:single`: Runs a single test in a specific testSuite
3. `test:suite`: Runs all the tests for a specific testSuite 


1. `test`: 运行您创建的所有 testSuite
2. `test:single`: 在特定的 testSuite 中运行单个测试
3. `test:suite`: 运行特定 testSuite 的所有测试

---

### Step 3: Create your various Test Suites _创建您的各种测试套件_

Once you have your `build.xml` file set up, you can now call the various TestSuites. 
Create a separate `TestSuiteXXX.xml` for the various modules that you would like to test. 
In your `TestSuiteXXX.xml`, you should have your default target testSuite call all the testcases you have definied: 


一旦你建立了你的 `build.xml` 文件，你现在可以调用各种 TestSuite。
为您要测试的各种模块创建一个单独的 `TestSuiteXXX.xml` 。
在您的 `TestSuiteXXX.xml` 中，您应该让默认目标 testSuite 调用您定义的所有测试用例：

---

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

This way you can either run all the test's in your Test Suite, or just run a specific one, all from `build.xml`!


这样你就可以在你的测试套件中运行所有的测试，或者只运行一个特定的测试，全部来自 `build.xml` ！

---

### Step 4: Create your various Tests _创建您的各种测试_

Now you need to write your various testcases. 
For more information on WebTest, please refer to the WebTest home page. 
If you have find you are duplicating pieces of XML, then place them in a `/includes` directory. 
If you have a single set of properties, then load them as part of `build.xml` by specifing them in your `build.properties` file. 
If you have multiple databases you need to connect to, then declare your sql connection properties in a `TestSuiteXXX.properties` file that you load on a per suite basis. 
In this example, we are using doing a clean insert into the database, and using the `MSSQL_CLEAN_INSERT` instead of `CLEAN_INSERT` because of the requirement to do identity column inserts. 


现在您需要编写各种测试用例。
有关 WebTest 的更多信息，请参阅 WebTest 主页。
如果您发现您正在复制 XML 片段，请将它们放在 `/includes` 目录中。
如果您只有一组属性，则通过在您的 `build.properties` 文件中指定它们，将它们作为 `build.xml` 的一部分加载。
如果您有多个需要连接的数据库，请在您在每个套件基础上加载的 `TestSuiteXXX.properties` 文件中声明您的 sql 连接属性。
在此示例中，我们使用对数据库进行干净插入，并使用 `MSSQL_CLEAN_INSERT` 而不是 `CLEAN_INSERT`，因为需要进行标识列插入。

---

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

## Sample Directory Layout _示例目录布局_

When you are done, you will have a series of files that look like this: 


完成后，您将拥有一系列文件，如下所示：

---

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
