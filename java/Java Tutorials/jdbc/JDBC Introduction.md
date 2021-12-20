# Lesson: JDBC Introduction _JDBC简介_


The JDBC API is a Java API that can access any kind of tabular data, especially data stored in a Relational Database.


JDBC API 是一种 Java API，可以访问任何类型的表格数据，尤其是存储在关系数据库中的数据。


JDBC helps you to write Java applications that manage these three programming activities:


JDBC 可帮助您编写管理以下三种编程活动的 Java 应用程序：


1. Connect to a data source, like a database
2. Send queries and update statements to the database
3. Retrieve and process the results received from the database in answer to your query


1. 连接到数据源，如数据库
2. 向数据库发送查询和更新语句
3. 检索和处理从数据库收到的结果以回答您的查询


The following simple code fragment gives a simple example of these three steps:


下面的简单代码片段给出了这三个步骤的简单示例：


```text
public void connectToAndQueryDatabase(String username, String password) {

    Connection con = DriverManager.getConnection(
                         "jdbc:myDriver:myDatabase",
                         username,
                         password);

    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM Table1");

    while (rs.next()) {
        int x = rs.getInt("a");
        String s = rs.getString("b");
        float f = rs.getFloat("c");
    }
}
```

This short code fragment instantiates a `DriverManager` object to connect to a database driver and log into the database, instantiates a `Statement` object that carries your SQL language query to the database; instantiates a ResultSet object that retrieves the results of your query, and executes a simple while loop, which retrieves and displays those results. 
It's that simple.


这个简短的代码片段实例化了一个 `DriverManager` 对象以连接到数据库驱动程序并登录到数据库，实例化一个 `Statement` 对象，该对象携带你的 SQL 语言查询到数据库；实例化一个检索查询结果的 ResultSet 对象，并执行一个简单的 while 循环，该循环检索并显示这些结果。
就这么简单。


## JDBC Product Components _JDBC 产品组件_


JDBC includes four components:


JDBC 包括四个组件：


1. **The JDBC API** —  The JDBC™ API provides programmatic access to relational data from the Java™ programming language. 
  Using the JDBC API, applications can execute SQL statements, retrieve results, and propagate changes back to an underlying data source. 
  The JDBC API can also interact with multiple data sources in a distributed, heterogeneous environment.

    The JDBC API is part of the Java platform, which includes the Java™ Standard Edition (Java™ SE ) and the Java™ Enterprise Edition (Java™ EE). 
    The JDBC 4.0 API is divided into two packages: `java.sql` and `javax.sql`. 
    Both packages are included in the Java SE and Java EE platforms.

2. **JDBC Driver Manager** —  The JDBC `DriverManager` class defines objects which can connect Java applications to a JDBC driver. 
  `DriverManager` has traditionally been the backbone of the JDBC architecture. 
  It is quite small and simple.

    The Standard Extension packages `javax.naming` and `javax.sql` let you use a `DataSource` object registered with a Java Naming and Directory Interface™ (JNDI) naming service to establish a connection with a data source. 
    You can use either connecting mechanism, but using a `DataSource` object is recommended whenever possible.

3. **JDBC Test Suite** —  The JDBC driver test suite helps you to determine that JDBC drivers will run your program. 
  These tests are not comprehensive or exhaustive, but they do exercise many of the important features in the JDBC API.

4. **JDBC-ODBC Bridge** —  The Java Software bridge provides JDBC access via ODBC drivers. 
  Note that you need to load ODBC binary code onto each client machine that uses this driver. 
  As a result, the ODBC driver is most appropriate on a corporate network where client installations are not a major problem, or for application server code written in Java in a three-tier architecture.


1. **The JDBC API** —  JDBC™ API 提供对来自 Java™ 编程语言的关系数据的编程访问。
  使用 JDBC API，应用程序可以执行 SQL 语句、检索结果并将更改传播回底层数据源。
  The JDBC API can also interact with multiple data sources in a distributed, heterogeneous environment.

    JDBC API 是 Java 平台的一部分，它包括 Java™ 标准版 (Java™ SE) 和 Java™ 企业版 (Java™ EE)。
    JDBC 4.0 API 分为两个包： `java.sql` 和 `javax.sql` 。
    这两个包都包含在 Java SE 和 Java EE 平台中。

2. **JDBC Driver Manager** —  JDBC `DriverManager` 类定义了可以将 Java 应用程序连接到 JDBC 驱动程序的对象。
  `DriverManager` 历来是 JDBC 架构的支柱。
  它非常小而简单。

    标准扩展包 `javax.naming` 和 `javax.sql` 允许您使用在 Java Naming and Directory Interface™ (JNDI) 命名服务中注册的 `DataSource` 对象来建立与数据源的连接。
    您可以使用任何一种连接机制，但建议尽可能使用 `DataSource` 对象。

3. **JDBC Test Suite** —  JDBC 驱动程序测试套件可帮助您确定 JDBC 驱动程序将运行您的程序。
  这些测试并不全面或详尽，但它们确实使用了 JDBC API 中的许多重要特性。

4. **JDBC-ODBC Bridge** —  Java 软件桥通过 ODBC 驱动程序提供 JDBC 访问。
  请注意，您需要将 ODBC 二进制代码加载到使用此驱动程序的每台客户端计算机上。
   因此，ODBC 驱动程序最适用于客户端安装不是主要问题的公司网络，或者适用于在三层体系结构中用 Java 编写的应用程序服务器代码。


This Trail uses the first two of these four JDBC components to connect to a database and then build a java program that uses SQL commands to communicate with a test Relational Database. 
The last two components are used in specialized environments to test web applications, or to communicate with ODBC-aware DBMSs.


此Trail 使用这四个JDBC 组件中的前两个连接到数据库，然后构建一个Java 程序，该程序使用SQL 命令与测试关系数据库进行通信。
最后两个组件在专门的环境中用于测试 Web 应用程序，或与支持 ODBC 的 DBMS 进行通信。

