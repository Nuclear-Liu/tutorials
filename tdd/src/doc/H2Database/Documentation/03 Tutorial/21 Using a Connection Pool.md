## Using a Connection Pool *使用连接池*

For H2, opening a connection is fast if the database is already open.
Still, using a connection pool improves performance if you open and close connections a lot.
A simple connection pool is included in H2.
It is based on the [Mini Connection Pool Manager]() from Christian d'Heureuse.
There are other, more complex, open source connection pools available, for example the [Apache Commons DBCP]().
For H2, it is about twice as faster to get a connection from the built-in connection pool than to get one using `DriverManager.getConnection()`.
The build-in connection pool is used as follows:


对于 H2，如果数据库已经打开，则打开连接会很快。
尽管如此，如果您经常打开和关闭连接，使用连接池可以提高性能。
H2 中包含一个简单的连接池。

----

```java
import java.sql.*;
import org.h2.jdbcx.JdbcConnectionPool;
public class Test {
    public static void main(String[] args) throws Exception {
        JdbcConnectionPool cp = JdbcConnectionPool.create(
            "jdbc:h2:~/test", "sa", "sa");
        for (int i = 0; i < args.length; i++) {
            Connection conn = cp.getConnection();
            conn.createStatement().execute(args[i]);
            conn.close();
        }
        cp.dispose();
    }
}
```
