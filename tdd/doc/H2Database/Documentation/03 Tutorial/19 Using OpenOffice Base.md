## Using OpenOffice Base *使用 OpenOffice Base*

OpenOffice.org Base supports database access over the JDBC API.
To connect to a H2 database using OpenOffice Base, you first need to add the JDBC driver to OpenOffice.
The steps to connect to a H2 database are:


OpenOffice.org Base 支持通过 JDBC API 访问数据库。
要使用 OpenOffice Base 连接到 H2 数据库，首先需要将 JDBC 驱动程序添加到 OpenOffice。
连接到 H2 数据库的步骤是：

----

* Start OpenOffice Writer, go to [Tools], [Options]
* Make sure you have selected a Java runtime environment in OpenOffice.org / Java
* Click [Class Path...], [Add Archive...]
* Select your h2 jar file (location is up to you, could be wherever you choose)
* Click [OK] (as much as needed), stop OpenOffice (including the Quickstarter)
* Start OpenOffice Base
* Connect to an existing database; select [JDBC]; [Next]
* Example datasource URL: `jdbc:h2:~/test`
* JDBC driver class: `org.h2.Driver`


* 启动 OpenOffice Writer，进入 [Tools]、[Options]
* 确保您在 OpenOffice.org Java 中选择了 Java 运行时环境
* 单击 [Class Path...]、 [Add Archive...]
* 选择你的 h2 jar 文件（位置由你决定，可以是你选择的任何地方）
* 单击 [OK] （根据需要），停止 OpenOffice（包括 Quickstarter）
* 启动 OpenOffice Base
* 连接到现有数据库；选择 [JDBC] ;  [Next]
* 示例数据源 URL： `jdbc:h2:~test`
* JDBC 驱动程序类： `org.h2.Driver`

----

Now you can access the database stored in the current users home directory.


现在您可以访问存储在当前用户主目录中的数据库。

----

To use H2 in NeoOffice (OpenOffice without X11):


在 NeoOffice 中使用 H2（没有 X11 的 OpenOffice）：

----

* In NeoOffice, go to [NeoOffice], [Preferences]
* Look for the page under [NeoOffice], [Java]
* Click [Class Path], [Add Archive...]
* Select your h2 jar file (location is up to you, could be wherever you choose)
* Click [OK] (as much as needed), restart NeoOffice.


* 在 NeoOffice 中，转到 [NeoOffice]、 [Preferences]
* 在 [NeoOffice]、 [Java]下查找页面
* 单击 [Class Path]、 [Add Archive...]
* 选择你的 h2 jar 文件（位置由你决定，可以是你选择的任何地方）
* 单击 [OK] （根据需要），重新启动 NeoOffice。

----

Now, when creating a new database using the "Database Wizard" :


现在，当使用“数据库向导”创建新数据库时：

----

* Click [File], [New], [Database].
* Select [Connect to existing database] and the select [JDBC]. 
  Click next.
* Example datasource URL: `jdbc:h2:~/test`
* JDBC driver class: `org.h2.Driver`


* 单击 [File]、 [New]、 [Database]。
* 选择 [Connect to existing database] ，然后选择 [JDBC] 。
  点击下一步。
* 示例数据源 URL： `jdbc:h2:~test`
* JDBC 驱动程序类： `org.h2.Driver`

----

Another solution to use H2 in NeoOffice is:


在 NeoOffice 中使用 H2 的另一种解决方案是：

----

* Package the h2 jar within an extension package
* Install it as a Java extension in NeoOffice


* 将 h2 jar 打包到扩展包中
* 在 NeoOffice 中将其安装为 Java 扩展

----

This can be done by create it using the NetBeans OpenOffice plugin. 
See also [Extensions Development](http://wiki.services.openoffice.org/wiki/Extensions_development_java).


这可以通过使用 NetBeans OpenOffice 插件创建它来完成。
另见 [Extensions Development]() 。

----
