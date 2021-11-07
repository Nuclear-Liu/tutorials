## Connecting to a Database using JDBC *使用 JDBC 连接到数据库*

To connect to a database, a Java application first needs to load the database driver, and then get a connection.
A simple way to do that is using the following code:


要连接到数据库，Java 应用程序首先需要加载数据库驱动程序，然后获取连接。
一种简单的方法是使用以下代码：

----

```java
import java.sql.*;
public class Test {
    public static void main(String[] a)
            throws Exception {
        Connection conn = DriverManager.
            getConnection("jdbc:h2:~/test", "sa", "");
        // add application code here
        conn.close();
    }
}
```

This code opens a connection (using `DriverManager.getConnection()`).
The driver name is `"org.h2.Driver"`.
The database URL always needs to start with `jdbc:h2:` to be recognized by this database.
The second parameter in the `getConnection()` call is the user name (`sa` for System Administrator in this example).
The third parameter is the password. 
In this database, user names are not case sensitive, but passwords are.


此代码打开一个连接（使用 `DriverManager.getConnection()` ）。
驱动程序名称是 `"org.h2.Driver"` 。
数据库 URL 总是需要以 `jdbc:h2:` 开头才能被这个数据库识别。
`getConnection()` 调用中的第二个参数是用户名（在本例中为系统管理员的 `sa` ）。
第三个参数是密码。
在这个数据库中，用户名不区分大小写，但密码是。

----
