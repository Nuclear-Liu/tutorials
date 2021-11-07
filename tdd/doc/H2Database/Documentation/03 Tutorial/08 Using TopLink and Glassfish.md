## Using TopLink and Glassfish *使用 TopLink 和 Glassfish*

To use H2 with Glassfish (or Sun AS), set the Datasource Classname to `org.h2.jdbcx.JdbcDataSource`.
You can set this in the GUI at Application Server - Resources - JDBC - Connection Pools, or by editing the file `sun-resources.xml`:
at element `jdbc-connection-pool`, set the attribute `datasource-classname` to `org.h2.jdbcx.JdbcDataSource`.


要将 H2 与 Glassfish（或 Sun AS）一起使用，请将数据源类名设置为 `org.h2.jdbcx.JdbcDataSource`。
您可以在应用程序服务器 - 资源 - JDBC - 连接池的 GUI 中进行设置，或通过编辑文件 `sun-resources.xml` ：
在元素 `jdbc-connection-pool` 处，将属性 `datasource-classname` 设置为 `org.h2.jdbcx.JdbcDataSource` 。

----

The H2 database is compatible with HSQLDB and PostgreSQL.
To take advantage of H2 specific features, use the `H2Platform`.
The source code of this platform is included in H2 at `src/tools/oracle/toplink/essentials/platform/database/DatabasePlatform.java.txt`.
You will need to copy this file to your application, and rename it to .java.
To enable it, change the following setting in persistence.xml:


H2 数据库兼容 HSQLDB 和 PostgreSQL。
要利用 H2 特定功能，请使用 `H2Platform` 。
该平台的源代码包含在 H2 中的 `srctoolsoracletoplinkessentialsplatformdatabaseDatabasePlatform.java.txt` 中。
您需要将此文件复制到您的应用程序，并将其重命名为 .java 。
要启用它，请更改 persistence.xml 中的以下设置：

----

```xml
<property
    name="toplink.target-database"
    value="oracle.toplink.essentials.platform.database.H2Platform"/>
```

In old versions of Glassfish, the property name is `toplink.platform.class.name`.


在旧版本的 Glassfish 中，属性名称是 `toplink.platform.class.name` 。

----

To use H2 within Glassfish, copy the h2*.jar to the directory `glassfish/glassfish/lib`.


要在 Glassfish 中使用 H2，请将 h2.jar 复制到目录 `glassfish/glassfish/lib` 。

----
