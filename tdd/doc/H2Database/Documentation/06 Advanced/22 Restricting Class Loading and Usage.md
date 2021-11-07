## Restricting Class Loading and Usage *限制类加载和使用*

By default there is no restriction on loading classes and executing Java code for admins.
That means an admin may call system functions such as `System.setProperty` by executing:


默认情况下，对于管理员加载类和执行 Java 代码没有限制。
这意味着管理员可以通过执行以下命令来调用系统函数，例如 `System.setProperty` ：

---

```sql
CREATE ALIAS SET_PROPERTY FOR "java.lang.System.setProperty";
CALL SET_PROPERTY('abc', '1');
CREATE ALIAS GET_PROPERTY FOR "java.lang.System.getProperty";
CALL GET_PROPERTY('abc');
```

To restrict users (including admins) from loading classes and executing code, the list of allowed classes can be set in the system property `h2.allowedClasses` in the form of a comma separated list of classes or patterns (items ending with `*`).
By default all classes are allowed.
Example:


为了限制用户（包括管理员）加载类和执行代码，可以在系统属性“h2.allowedClasses”中以逗号分隔的类或模式列表（以``结尾的项目）的形式设置允许的类列表。
默认情况下允许所有类。
例子：

---

```sql
java -Dh2.allowedClasses=java.lang.Math,com.acme.*
```

This mechanism is used for all user classes, including database event listeners, trigger classes, user-defined functions, user-defined aggregate functions, and JDBC driver classes (with the exception of the H2 driver) when using the H2 Console.


该机制适用于所有用户类，包括数据库事件监听器、触发器类、用户自定义函数、用户自定义聚合函数和使用H2控制台时的JDBC驱动程序类（H2驱动程序除外）。

---
