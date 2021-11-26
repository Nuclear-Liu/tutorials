## Ant Integration _Ant 集成_

### Installation _安装_

The steps required to add the DbUnit task to your system are:


将 DbUnit 任务添加到系统所需的步骤是：

---

1. Add the DbUnit jar to Ant's classpath. 
    You may alternatively use the '`classpath`' attribute of `<taskdef>`.

2. Add a `<taskdef>` element to your build script as follows: 

    `<taskdef name="dbunit" classname="org.dbunit.ant.DbUnitTask"/>`

3. Use the task in the rest of the buildfile.


1. 将 DbUnit jar 添加到 Ant 的类路径。
    您也可以使用 `<taskdef>` 的 '`classpath`' 属性。

2. 将 `<taskdef>` 元素添加到您的构建脚本中，如下所示：

   `<taskdef name="dbunit" classname="org.dbunit.ant.DbUnitTask"/>`

3. 在构建文件的其余部分使用该任务。

---

## Usage _用法_

Executes either a single transaction, or a group of transactions, under the DbUnit database testing framework.


在 DbUnit 数据库测试框架下执行单个事务或一组事务。

---

### Parameters _参数_

| Attribute | Description | Required |
| ---- | ---- | ---- |
| `driver` | Class name of the jdbc driver. Must be available on the classpath (either system classpath or nested classpath) | Yes |
| `url` | Database connection url | Yes |
| `userid` | Database username | Yes |
| `password` | Database password | Yes |
| `schema` | Database schema | No |
| `classpath` | Classpath used to load driver. | No (use system classpath) |
| `useQualifiedTableNames` | Set the qualified table namesfeature. Defaults to `false`. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `supportBatchStatement` | Set the batched statementfeature. Defaults to `false`. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `datatypeWarning` | Set the data type warningfeature. Defaults to `true`. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `escapePattern` | Set the escape patternproperty. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `datatypeFactory` | Set the datatype factoryproperty. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `skipOracleRecycleBinTables` | Set the `skipOracleRecycleBinTablesfeature`. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `batchSize` | Set the batchSizeproperty. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `fetchSize` | Set the fetchSizeproperty. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |
| `allowEmptyFields` | Set the allowEmptyFieldsfeature. `@deprecated` since 2.4. Use nested `dbconfig` element instead. | No |


| 属性 | 描述 | 必需的 |
| ---- | ---- | ---- |
| `driver` | jdbc 驱动的类名。必须在类路径上可用（系统类路径或嵌套类路径） | Yes |
| `url` | 数据库连接 url | Yes |
| `userid` | 数据库用户名 | Yes |
| `password` | 数据库密码 | Yes |
| `schema` | 数据库 schema | No |
| `classpath` | 用于加载驱动程序的类路径。 | No (use system classpath) |
| `useQualifiedTableNames` | 设置合格的表名称功能。默认为 `false` 。 `@deprecated` 自 2.4 起。请改用嵌套的 `dbconfig` 元素。 | No |
| `supportBatchStatement` | 设置批处理语句功能。默认为 `false` 。 `@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |
| `datatypeWarning` | 设置数据类型警告功能。默认为 `true` 。 `@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |
| `escapePattern` | 设置转义模式属性。`@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |
| `datatypeFactory` | 设置数据类型工厂属性。`@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |
| `skipOracleRecycleBinTables` | 设置 `skipOracleRecycleBinTables` 功能。`@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |
| `batchSize` | 设置 `batchSize` 属性。 `@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |
| `fetchSize` | 设置 `fetchSize` 属性。 `@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |
| `allowEmptyFields` | 设置 `allowEmptyFields` 功能。 `@deprecated` 自 2.4 起。改用嵌套的 `dbconfig` 元素。 | No |

---

### Parameters specified as nested elements _指定为嵌套元素的参数_

|  |  |
| ---- | ---- |
|  |  |

## Examples _示例_

### Update operation with specified JDBC driver jar _使用指定的 JDBC 驱动程序 jar 进行更新操作_

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

#### Insert and update operations _插入和更新操作_

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <operation type="INSERT" src="insertFile.xml"/>
    <operation type="UPDATE" src="updateFile.xml"/>
</dbunit>
```

### Database data export to XML _数据库数据导出到 XML_

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <export dest="export.xml"/>
</dbunit>
```

### Database structure export to DTD _数据库结构导出到 DTD_

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <export dest="export.dtd" format="dtd"/>
</dbunit>
```

### Partial database data export _部分数据库数据导出_

Export two tables: FOO, resulting from specified query and BAR entire content


导出两个表：FOO，产生于指定查询和 BAR 整个内容

---

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

### Database data comparison _数据库数据对比_

```xml
<dbunit driver="com.vendor.jdbc.Driver"
        url="jdbc:vendor:mydatabase"
        userid="user"
        password="password">
    <compare src="expectedData.xml"/>
</dbunit>
```

### Partial database data comparison _部分数据库数据对比_

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

### Partial CSV export using queryset reference. _使用查询集引用的部分 CSV 导出。_

Export employees with specified userNames, as well as all managers.


导出具有指定用户名的员工以及所有经理。

---

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

### DatabaseConfig example with nested configuration

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
