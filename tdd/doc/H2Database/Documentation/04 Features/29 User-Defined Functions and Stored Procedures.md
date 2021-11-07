## User-Defined Functions and Stored Procedures *用户定义的函数和存储过程*

In addition to the built-in functions, this database supports user-defined Java functions.
In this database, Java functions can be used as stored procedures as well.
A function must be declared (registered) before it can be used.
A function can be defined using source code, or as a reference to a compiled class that is available in the classpath.
By default, the function aliases are stored in the current schema.


除了内置函数之外，该数据库还支持用户定义的 Java 函数。
在这个数据库中，Java 函数也可以用作存储过程。
一个函数在使用之前必须被声明（注册）。
函数可以使用源代码定义，也可以作为对类路径中可用的已编译类的引用来定义。
默认情况下，函数别名存储在当前模式中。

---

### Referencing a Compiled Method *引用编译方法*

When referencing a method, the class must already be compiled and included in the classpath where the database is running.
Only static Java methods are supported; both the class and the method must be public.
Example Java class:


引用方法时，该类必须已经编译并包含在运行数据库的类路径中。
仅支持静态 Java 方法；类和方法都必须是公共的。
示例 Java 类：

---

```java
package acme;
import java.math.*;
public class Function {
    public static boolean isPrime(int value) {
        return new BigInteger(String.valueOf(value)).isProbablePrime(100);
    }
}
```

The Java function must be registered in the database by calling `CREATE ALIAS ... FOR`:


Java 函数必须通过调用 `CREATE ALIAS ... FOR` 在数据库中注册：

---

```sql
CREATE ALIAS IS_PRIME FOR "acme.Function.isPrime";
```

For a complete sample application, see `src/test/org/h2/samples/Function.java`.


有关完整的示例应用程序，请参阅 `src/test/org/h2/samples/Function.java` 。

---

### Declaring Functions as Source Code *将函数声明为源代码*

When defining a function alias with source code, the database tries to compile the source code using the Sun Java compiler (the class `com.sun.tools.javac.Main`) if the `tools.jar` is in the classpath.
If not, `javac` is run as a separate process.
Only the source code is stored in the database; the class is compiled each time the database is re-opened.
Source code is usually passed as dollar quoted text to avoid escaping problems, however single quotes can be used as well.
Example:


当使用源代码定义函数别名时，如果`tools.jar` 在类路径中，数据库会尝试使用Sun Java 编译器（类`com.sun.tools.javac.Main`）编译源代码。
如果没有， `javac` 作为一个单独的进程运行。
只有源代码存储在数据库中；每次重新打开数据库时都会编译该类。
源代码通常作为美元引用文本传递以避免转义问题，但也可以使用单引号。
例子：

---

```sql
CREATE ALIAS NEXT_PRIME AS $$
String nextPrime(String value) {
    return new BigInteger(value).nextProbablePrime().toString();
}
$$;
```

By default, the three packages `java.util`, `java.math`, `java.sql` are imported.
The method name (`nextPrime` in the example above) is ignored.
Method overloading is not supported when declaring functions as source code, that means only one method may be declared for an alias.
If different import statements are required, they must be declared at the beginning and separated with the tag `@CODE`:


默认情况下，导入了三个包`java.util`、`java.math`、`java.sql`。
方法名称（上例中的 `nextPrime` ）被忽略。
将函数声明为源代码时不支持方法重载，这意味着只能为别名声明一个方法。
如果需要不同的 import 语句，必须在开头声明并用标签 `@CODE` 分隔：

---

```sql
CREATE ALIAS IP_ADDRESS AS $$
import java.net.*;
@CODE
String ipAddress(String host) throws Exception {
    return InetAddress.getByName(host).getHostAddress();
}
$$;
```

The following template is used to create a complete Java class:


以下模板用于创建完整的 Java 类：

---

```sql
package org.h2.dynamic;
< import statements before the tag @CODE; if not set:
import java.util.*;
import java.math.*;
import java.sql.*;
>
public class <aliasName> {
    public static <sourceCode>
}
```

### Method Overloading *方法重载*

Multiple methods may be bound to a SQL function if the class is already compiled and included in the classpath.
Each Java method must have a different number of arguments.
Method overloading is not supported when declaring functions as source code.


如果类已经编译并包含在类路径中，则可以将多个方法绑定到 SQL 函数。
每个 Java 方法必须具有不同数量的参数。
将函数声明为源代码时，不支持方法重载。

---

### Function Data Type Mapping *功能数据类型映射*

Functions that accept non-nullable parameters such as int will not be called if one of those parameters is NULL. 
Instead, the result of the function is NULL. 
If the function should be called if a parameter is NULL, you need to use java.lang.Integer instead.


如果这些参数之一为 NULL，则不会调用接受不可为 null 的参数（如 int）的函数。
相反，该函数的结果是 NULL。
如果参数为 NULL 时应调用该函数，则需要改用 java.lang.Integer 。

---

SQL types are mapped to Java classes and vice-versa as in the JDBC API.
For details, see [Data Types]().
There are a few special cases: `java.lang.Object` is mapped to `OTHER` (a serialized object).
Therefore, `java.lang.Object` can not be used to match all SQL types (matching all SQL types is not supported).
The second special case is `Object[]`: arrays of any class are mapped to `ARRAY`.
Objects of type `org.h2.value.Value` (the internal value class) are passed through without conversion.


SQL 类型映射到 Java 类，反之亦然，就像在 JDBC API 中一样。
有关详细信息，请参阅[数据类型]()。
有一些特殊情况：`java.lang.Object` 被映射到 `OTHER` （一个序列化的对象）。
因此， `java.lang.Object` 不能用于匹配所有 SQL 类型（不支持匹配所有 SQL 类型）。
第二个特殊情况是 `Object[]` ：任何类的数组都映射到 `ARRAY` 。
类型为 `org.h2.value.Value` （内部值类）的对象无需转换即可通过。

---

### Functions That Require a Connection *需要连接的函数*

If the first parameter of a Java function is a `java.sql.Connection`, then the connection to database is provided.
This connection does not need to be closed before returning.
When calling the method from within the SQL statement, this connection parameter does not need to be (can not be) specified.


如果 Java 函数的第一个参数是 `java.sql.Connection` ，则提供到数据库的连接。
在返回之前不需要关闭此连接。
从 SQL 语句内部调用该方法时，不需要（不能）指定此连接参数。

---

### Functions Throwing an Exception *引发异常的函数*

If a function throws an exception, then the current statement is rolled back and the exception is thrown to the application.
SQLException are directly re-thrown to the calling application; all other exceptions are first converted to a SQLException.


如果函数抛出异常，则回滚当前语句并将异常抛出给应用程序。
SQLException 被直接重新抛出给调用应用程序；所有其他异常首先转换为 SQLException。

---

### Functions Returning a Result Set *返回集和结果的函数*

Functions may returns a result set.
Such a function can be called with the `CALL` statement:


函数可能会返回一个结果集。
可以使用 `CALL` 语句调用这样的函数：

---

```
public static ResultSet query(Connection conn, String sql) throws SQLException {
    return conn.createStatement().executeQuery(sql);
}

CREATE ALIAS QUERY FOR "org.h2.samples.Function.query";
CALL QUERY('SELECT * FROM TEST');
```

### Using SimpleResultSet *使用 SimpleResultSet*

A function can create a result set using the `SimpleResultSet` tool:


函数可以使用 `SimpleResultSet` 工具创建结果集：

---

````
import org.h2.tools.SimpleResultSet;
...
public static ResultSet simpleResultSet() throws SQLException {
    SimpleResultSet rs = new SimpleResultSet();
    rs.addColumn("ID", Types.INTEGER, 10, 0);
    rs.addColumn("NAME", Types.VARCHAR, 255, 0);
    rs.addRow(0, "Hello");
    rs.addRow(1, "World");
    return rs;
}

CREATE ALIAS SIMPLE FOR "org.h2.samples.Function.simpleResultSet";
CALL SIMPLE();
````

### Using a Function as a Table *使用函数作为表格*

A function that returns a result set can be used like a table.
However, in this case the function is called at least twice: 
first while parsing the statement to collect the column names (with parameters set to `null` where not known at compile time).
And then, while executing the statement to get the data (maybe multiple times if this is a join).
If the function is called just to get the column list, the URL of the connection passed to the function is `jdbc:columnlist:connection`.
Otherwise, the URL of the connection is `jdbc:default:connection`.


返回结果集的函数可以像表格一样使用。
但是，在这种情况下，该函数至少被调用两次：
首先在解析语句以收集列名时（参数设置为 `null`，在编译时未知）。
然后，在执行语句以获取数据时（如果这是连接，可能会多次）。
如果调用该函数只是为了获取列列表，则传递给该函数的连接 URL 为 `jdbc:columnlist:connection` 。
否则，连接的 URL 是 `jdbc:default:connection` 。

---

```
public static ResultSet getMatrix(Connection conn, Integer size)
        throws SQLException {
    SimpleResultSet rs = new SimpleResultSet();
    rs.addColumn("X", Types.INTEGER, 10, 0);
    rs.addColumn("Y", Types.INTEGER, 10, 0);
    String url = conn.getMetaData().getURL();
    if (url.equals("jdbc:columnlist:connection")) {
        return rs;
    }
    for (int s = size.intValue(), x = 0; x < s; x++) {
        for (int y = 0; y < s; y++) {
            rs.addRow(x, y);
        }
    }
    return rs;
}

CREATE ALIAS MATRIX FOR "org.h2.samples.Function.getMatrix";
SELECT * FROM MATRIX(4) ORDER BY X, Y;
```
