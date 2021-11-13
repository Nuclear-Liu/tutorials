## Ant Integration

### Installation

The steps required to add the DbUnit task to your system are:

1. Add the DbUnit jar to Ant's classpath. You may alternatively use the 'classpath' attribute of <taskdef>.

2. Add a <taskdef> element to your build script as follows: 

    `<taskdef name="dbunit" classname="org.dbunit.ant.DbUnitTask"/>`

3. Use the task in the rest of the buildfile.

## Usage

Executes either a single transaction, or a group of transactions, under the DbUnit database testing framework.

### Parameters

| Attribute | Description | Required |
| ---- | ---- | ---- |
| `driver` | Class name of the jdbc driver. Must be available on the classpath (either system classpath or nested classpath) | Yes |
| `url` | Database connection url | Yes |
| `userid` | Database username | Yes |
| `password` | Database password | Yes |
| `schema` | Database schema | No |
| `classpath` | Classpath used to load driver. | No (use system classpath) |
| `useQualifiedTableNames` | Set the qualified table namesfeature. Defaults to false. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `supportBatchStatement` | Set the batched statementfeature. Defaults to false. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `datatypeWarning` | Set the data type warningfeature. Defaults to true. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `escapePattern` | Set the escape patternproperty. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `datatypeFactory` | Set the datatype factoryproperty. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `skipOracleRecycleBinTables` | Set the skipOracleRecycleBinTablesfeature. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `batchSize` | Set the batchSizeproperty. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `fetchSize` | Set the fetchSizeproperty. @deprecated since 2.4. Use nested dbconfig element instead. | No |
| `allowEmptyFields` | Set the allowEmptyFieldsfeature. @deprecated since 2.4. Use nested dbconfig element instead. | No |

### Parameters specified as nested elements

|  |  |
| ---- | ---- |
|  |  |

## Examples

### Update operation with specified JDBC driver jar

```xml
<dbunit driver="com.vendor.jdbc.Driver"
    url="jdbc:vendor:mydatabase"
    userid="user"
    password="password">
    <classpath>
        <pathelement location="/some/jdbc.jar"/>
    </classpath>
    <operation type="UPDATE" src="updateFile.xml"/>
</dbunit>
```

Insert and update operations

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <operation type="INSERT" src="insertFile.xml"/>
    <operation type="UPDATE" src="updateFile.xml"/>
</dbunit>
```

Database data export to XML

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <export dest="export.xml"/>
</dbunit>
```

Database structure export to DTD

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <export dest="export.dtd" format="dtd"/>
</dbunit>
```

Partial database data export

Export two tables: FOO, resulting from specified query and BAR entire content

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <export dest="partial.xml">
        <query name="FOO" sql="SELECT COL1, COL2 FROM FOO WHERE COL1=4"/>
        <table name="BAR"/>
    </export>
</dbunit>
```

Database data comparison

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <compare src="expectedData.xml"/>
</dbunit>
```

Partial database data comparison

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <compare src="expectedData.xml">
        <query name="FOO" sql="SELECT COL1, COL2 FROM FOO WHERE COL1=4"/>
        <table name="BAR"/>
    </compare>
</dbunit>
```

Partial CSV export using queryset reference.

Export employees with specified userNames, as well as all managers.

```text
<!-- ======== Define the reference. Note that before ant 1.6
              this would have to be within a target. ====== -->
<queryset id="employees">
   <query name="EMPLOYEE"
     sql="SELECT * FROM EMPLOYEE WHERE EMP_ID IN (@subQuery@)"/>
   <query name="EMP_ADDRESS" sql="
       SELECT B.* FROM EMPLOYEE A, EMP_ADDRESS B
       WHERE A.EMP_ID IN (@subQuery@)
       AND B.EMP_ID = A.EMP_ID"/>
</queryset>
.....
<!-- ========= Use the reference ====================== -->

<dbunit driver="${jdbcDriver}"
        url="${jdbcURL}" 
        userid="${jdbcUser}" 
        password="${jdbcPassword}">
  <export dest="someDir" format="csv">
    <queryset refid="employees">
       <filterset>
         <filter token="subQuery" value="
           SELECT EMP_ID FROM EMPLOYEE WHERE USER_NAME IN('joe', 'schmo')"/>
       </filterset>
    </queryset>
    <queryset refid="employees">
       <filterset>
         <filter token="subQuery" value="
           SELECT EMP_ID FROM EMPLOYEE WHERE IS_MANAGER = 1"/>
       </filterset>
    </queryset>
  </export>
</dbunit>
```

DatabaseConfig example with nested configuration

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <dbconfig>
        <property name="datatypeFactory" value="org.dbunit.ext.oracle.OracleDataTypeFactory" />
        <!-- Is internally split to a string array using the comma as separator -->
        <property name="tableType" value="TABLE,SYNONYM" />
        <feature name="batchedStatements" value="true" />
    </dbconfig>

    ...
</dbunit>
```
