## CSV (Comma Separated Values) Support *CSV （逗号分隔值）支持*

The CSV file support can be used inside the database using the functions `CSVREAD` and `CSVWRITE`, or it can be used outside the database as a standalone tool.


CSV 文件支持可以使用函数 `CSVREAD` 和 `CSVWRITE` 在数据库内部使用，也可以作为独立工具在数据库外部使用。

----

### Reading a CSV File from Within a Database *从数据库中读取 CSV 文件*

A CSV file can be read using the function `CSVREAD`. 
Example:


可以使用函数“CSVREAD”读取 CSV 文件
例子：

----

```sql
SELECT * FROM CSVREAD('test.csv');
```

Please note for performance reason, `CSVREAD` should not be used inside a join.
Instead, import the data first (possibly into a temporary table), create the required indexes if necessary, and then query this table.


请注意，出于性能原因，不应在连接中使用 `CSVREAD` 。
相反，首先导入数据（可能导入临时表），必要时创建所需的索引，然后查询该表。

----

### Importing Data from a CSV File *从 CSV 文件导入数据*

A fast way to load or import data (sometimes called 'bulk load') from a CSV file is to combine table creation with import.
Optionally, the column names and data types can be set when creating the table.
Another option is to use `INSERT INTO ... SELECT`.


从 CSV 文件加载或导入数据（有时称为“批量加载”）的一种快速方法是将表创建与导入相结合。
或者，可以在创建表时设置列名和数据类型。
另一种选择是使用 `INSERT INTO ... SELECT` 。

----

```sql
CREATE TABLE TEST AS SELECT * FROM CSVREAD('test.csv');
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))
    AS SELECT * FROM CSVREAD('test.csv');
```

### Writing a CSV File from Within a Database *从数据库中写入 CSV 文件*

The built-in function `CSVWRITE` can be used to create a CSV file from a query. 
Example:


内置函数 `CSVWRITE` 可用于从查询创建 CSV 文件。
例子：

----

```sql
CREATE TABLE TEST(ID INT, NAME VARCHAR);
INSERT INTO TEST VALUES(1, 'Hello'), (2, 'World');
CALL CSVWRITE('test.csv', 'SELECT * FROM TEST');
```

### Writing a CSV File from a Java Application *从 Java 应用程序写入 CSV 文件*

The `Csv` tool can be used in a Java application even when not using a database at all. 
Example:


即使根本不使用数据库，也可以在 Java 应用程序中使用 `Csv` 工具。
例子：

----

```java
import java.sql.*;
import org.h2.tools.Csv;
import org.h2.tools.SimpleResultSet;
public class TestCsv {
    public static void main(String[] args) throws Exception {
        SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("NAME", Types.VARCHAR, 255, 0);
        rs.addColumn("EMAIL", Types.VARCHAR, 255, 0);
        rs.addRow("Bob Meier", "bob.meier@abcde.abc");
        rs.addRow("John Jones", "john.jones@abcde.abc");
        new Csv().write("data/test.csv", rs, null);
    }
}
```

### Reading a CSV File from a Java Application *从 Java 应用程序读取 CSV 文件*

It is possible to read a CSV file without opening a database. 
Example:


可以在不打开数据库的情况下读取 CSV 文件。
例子：

----

```java
import java.sql.*;
import org.h2.tools.Csv;
public class TestCsv {
    public static void main(String[] args) throws Exception {
        ResultSet rs = new Csv().read("data/test.csv", null, null);
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            for (int i = 0; i < meta.getColumnCount(); i++) {
                System.out.println(
                    meta.getColumnLabel(i + 1) + ": " +
                    rs.getString(i + 1));
            }
            System.out.println();
        }
        rs.close();
    }
}
```
