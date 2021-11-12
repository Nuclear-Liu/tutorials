Overview

[PrepAndExpectedTestCase](), and its default implementation [DefaultPrepAndExpectedTestCase](), is a test case formally supporting prep data and expected data concepts and definitions.

**Prep data**

is the setup data needed in the database for the test to run.

**Expected data**

is the data to compare with one or more database tables verifying the test ran successfully.

[PrepAndExpectedTestCase]() easily allows defining which are the setup datasets (prep) and the verify datasets (expected).

It conveniently packages a turn-key test setup and verification process in one:

1. Loads the prep dataset files and inserts their data into the database tables
2. Runs the test steps (if specified as a runTest() PrepAndExpectedTestCaseSteps parameter)
3. Verifies table state matches the expected datasets for the specified VerifyTableDefinitions
4. Cleans up the tables listed in the prep and expected datasets (per the configured teardown operation)

## Usage

### Configure

Configure this class in one of two ways:

1. Dependency inject it as its interface into a test class. 

```text
@Inject
private PrepAndExpectedTestCase testCase;
```

Create it by configuring an instance of its interface (start with DefaultPrepAndExpectedTestCase and extend & override if necessary), injecting a IDatabaseTester and a DataFileLoader using the databaseTester and a dataFileLoader properties (see Configuration Example Using Spring below).

2. Extend it in a test class. Obtain IDatabaseTester and DataFileLoader instances (possibly dependency injecting them into the test class) and set them accordingly, probably in a setup type of method, such as: 

```text
@Before
public void setDbunitTestDependencies()
{
    setDatabaseTester(databaseTester);
    setDataFileLoader(dataFileLoader);
}
```

### Run Tests

[PrepAndExpectedTestCase]() has two ways to setup, execute, and clean up tests:

    1. Encapsulate the test steps in [PrepAndExpectedTestCaseSteps]() and call the runTest() method. Note, this requires Release 2.5.2 and newer.
    2. Call the configureTest(), preTest(), and postTest() methods. Note there is a preTest() convenience method that takes the same parameters as the configureTest() method; use it instead of using both configureTest() and preTest(). Where the test calls those methods depends on data needs:
        * For the whole test case, i.e. in setUp() and tearDown() or @Before and @After.
        * In each test method.
        * Or some combination of both test case setup/teardown and test methods.

(see [Test Examples]() below)

## Configuration Example Using Spring

The following configuration shows customizing [DatabaseConfig]() and enables dependency injecting the created [PrepAndExpectedTestCase]().

```java
@Configuration
@Validated
public class DbUnitConfiguration
{
    /**
     * Extend DefaultPrepAndExpectedTestCase to customize DatabaseConfig.
     */
    private class MyPrepAndExpectedTestCase
            extends DefaultPrepAndExpectedTestCase
    {
        public MyPrepAndExpectedTestCase(
                final DataFileLoader dataFileLoader,
                final IDatabaseTester databaseTester)
        {
            super(dataFileLoader, databaseTester);
        }

        @Override
        protected void setUpDatabaseConfig(final DatabaseConfig config)
        {
            // set properties as needed

            config.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);

            // set the specific IDataTypeFactory if needed
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new XxxDataTypeFactory());
        }
    }

    /**
     * Create dbUnit {@link PrepAndExpectedTestCase} for running dbUnit database
     * tests.
     *
     * @param dataFileLoader
     *            The {@link DataFileLoader} used to load the test's specified
     *            data files.
     * @param databaseTester
     *            The {@link IDatabaseTester} used to run the tests against the
     *            database.
     * @return Configured dbUnit {@link PrepAndExpectedTestCase} for running
     *         dbUnit database tests.
     */
    @Bean
    public PrepAndExpectedTestCase prepAndExpectedTestCase(
            final DataFileLoader dataFileLoader,
            final IDatabaseTester databaseTester)
    {
        return new MyPrepAndExpectedTestCase(dataFileLoader, databaseTester);
    }

    /**
     * Create dbUnit {@link DataFileLoader} for loading the test's dbUnit data
     * files.
     *
     * @param ddr
     *            Your local class containing the replacement definitions.
     * @return Configured dbUnit {@link DataFileLoader} for loading the test's
     *         dbUnit data files.
     */
    @Bean
    public DataFileLoader dataFileLoader(final DbunitDataReplacement ddr)
    {
        final Map<String, Object> replacementObjects = ddr.getReplacementObjects();
        final Map<String, Object> replacementSubstrings = ddr.getReplacementSubstrings();
        return new FlatXmlDataFileLoader(replacementObjects, replacementSubstrings);
    }

    /**
     * Create dbUnit {@link IDatabaseTester}.
     * 
     * @param dataSource
     *            The {@link DataSource} for the dbUnit test to use.
     * @return Configured dbUnit {@link IDatabaseTester}.
     */
    @Bean
    public IDatabaseTester databaseTester(final DataSource dataSource)
    {
        final DataSource dataSourceProxy = new TransactionAwareDataSourceProxy(dataSource);

        final IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSourceProxy);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

        return databaseTester;
    }
}
```

```java
/**
 * Class containing replacement objects and replacement substrings for
 * substitution in dbUnit datasets.
 */
@Component
public class DbUnitDataReplacement
{
    private final Map<String, Object> replacementObjects = new HashMap<>();
    private final Map<String, Object> replacementSubstrings = new HashMap<>();

    public DbUnitDataReplacement()
    {
        populateReplacementObjects();
        populateReplacementSubstrings();
    }

    /**
     * Make replacement objects and populate the map with them.
     */
    private void populateReplacementObjects()
    {
        replacementObjects.put("[IGNORE]", null);
        replacementObjects.put("[NULL]", null);
        replacementObjects.put("[TIMESTAMP_TODAY]", TestDatabaseDates.TIMESTAMP_TODAY);
        replacementObjects.put("[TIMESTAMP_TOMORROW]", TestDatabaseDates.TIMESTAMP_TOMORROW);
        replacementObjects.put("[TIMESTAMP_YESTERDAY]", TestDatabaseDates.TIMESTAMP_YESTERDAY);
    }

    /**
     * Make replacement substrings and populate the map with them.
     */
    private void populateReplacementSubstrings()
    {
    }

    public Map<String, Object> getReplacementObjects()
    {
        return replacementObjects;
    }

    public Map<String, Object> getReplacementSubstrings()
    {
        return replacementSubstrings;
    }
}
```

```java
/**
 * Dates for testing with database dates.
 */
@Component
public class TestDatabaseDates
{
    public static final Period ONE_DAY = Period.ofDays(1);

    public static final Instant NOW = Instant.now();

    public static final Timestamp TIMESTAMP_TODAY = asTimestamp(NOW);
    public static final Timestamp TIMESTAMP_TOMORROW = asTimestamp(NOW.plus(ONE_DAY));
    public static final Timestamp TIMESTAMP_YESTERDAY = asTimestamp(NOW.minus(ONE_DAY));

    public static Timestamp asTimestamp(final Instant instant)
    {
        return Timestamp.from(instant);
    }
}
```

## Test Examples

Note: use good constant names for table names and table definitions, not generic "TABLEn" as used in these examples.

These examples show:

1. Before the test runs, insert into the database the dataset contents of the files (represented by the constants COMMON_TABLE1, COMMON_TABLE2, TABLE3_PREP, TABLE4_PREP) as configured by the setup operation (defined outside of the test).
2. After the test runs, verify table3 and table5 contents are the same as the expected data files and according to the VerifyTableDefinitions.
3. After the test runs, cleanup tables as configured by the teardown operation (defined outside of the test).

### Common Classes for Examples

It is helpful to make classes for common test table configurations as the configuration is usually the same for most tests with some tests slightly deviating. The following examples show some of these options.

#### TableNames

A simple class of table names, providing consistency and preventing typos.

Make this a production class when additionally specifying table names in classes such as entities and repositories/DAOs.

```java
public abstract class TableNames
{
    public static final String TABLE3 = "table3";
    public static final String TABLE5 = "table5";
}
```

#### ColumnNames

A simple class of column names, providing consistency and preventing typos.

Make this a production class when additionally specifying column names in classes such as entities and repositories/DAOs.

```java
public abstract class ColumnNames
{
    public static final String COLUMN1 = "column1";
}
```
