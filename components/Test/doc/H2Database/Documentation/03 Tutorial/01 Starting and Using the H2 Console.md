## Starting and Using the H2 Console *启动和使用 H2 控制台*

The H2 Console application lets you access a database using a browser.
This can be a H2 database, or another database that supports the JDBC API.


H2 控制台应用程序允许您使用浏览器访问数据库。
这可以是 H2 数据库，也可以是其他支持 JDBC API 的数据库。

----

![console-2](console-2.png)

This is a client/server application, so both a server and a client (a browser) are required to run it.


这是一个客户端服务器应用程序，因此需要服务器和客户端（浏览器）来运行它。

----

Depending on your platform and environment, there are multiple ways to start the H2 Console:


根据您的平台和环境，有多种方式可以启动 H2 控制台：

----

| OS | Start |
| ---- | ---- |
| Windows | Click [Start], [All Programs], [H2], and [H2 Console (Command Line)] An icon will be added to the system tray: ![db-64-t](db-64-t.png) If you don't get the window and the system tray icon, then maybe Java is not installed correctly (in this case, try another way to start the application). A browser window should open and point to the login page at `http://localhost:8082`.  |
| Windows | Open a file browser, navigate to `h2/bin` , and double click on `h2.bat` . A console window appears. If there is a problem, you will see an error message in this window. A browser window will open and point to the login page (URL: `http://localhost:8082`).  |
| Any | Double click on the `h2*.jar` file. This only works if the `.jar` suffix is associated with Java. |
| Any | Open a console window, navigate to the directory `h2/bin` , and type: `java -jar h2*.jar` |


| OS | 启动 |
| ---- | ---- |
| Windows | 点击 [Start] 、 [All Programs] 、 [H2] 和 [H2 Console (Command Line)] 一个图标会被添加到系统托盘： ![db-64-t](db-64-t.png) 如果您没有看到窗口和系统托盘图标，则可能是 Java 没有正确安装（在这种情况下，请尝试另一种方式来启动应用程序）。 应该打开一个浏览器窗口并指向“http:localhost:8082”的登录页面。 |
| Windows | 打开文件浏览器，导航到 `h2/bin` ，然后双击 `h2.bat` 。 出现一个控制台窗口。 如果出现问题，您将在此窗口中看到错误消息。 浏览器窗口将打开并指向登录页面（URL：`http:localhost:8082`）。  |
| Any | 双击 `h2*.jar` 文件。 这仅在 `.jar` 后缀与 Java 相关联时有效。 |
| Any | 打开控制台窗口，导航到目录 `h2/bin` ，然后输入： `java -jar h2*.jar` |

----

If the console startup procedure is unable to locate the default system web browser, an error message may be displayed.
It is possible to explicitly tell H2 which program/script to use when opening a system web browser by setting either the BROWSER environment variable, or the h2.browser java property.


如果控制台启动程序无法找到默认的系统 Web 浏览器，则可能会显示错误消息。
通过设置 BROWSER 环境变量或 `h2.browser` java 属性，可以明确告诉 H2 在打开系统 Web 浏览器时使用哪个程序脚本。

----

### Firewall *防火墙*

If you start the server, you may get a security warning from the firewall (if you have installed one).
If you don't want other computers in the network to access the application on your machine, you can let the firewall block those connections.
The connection from the local machine will still work.
Only if you want other computers to access the database on this computer, you need allow remote connections in the firewall.


如果您启动服务器，您可能会收到来自防火墙的安全警告（如果您已安装）。
如果您不希望网络中的其他计算机访问您计算机上的应用程序，您可以让防火墙阻止这些连接。
来自本地机器的连接仍然有效。
只有当您希望其他计算机访问本计算机上的数据库时，您才需要在防火墙中允许远程连接。

----

It has been reported that when using Kaspersky 7.0 with firewall, the H2 Console is very slow when connecting over the IP address.
A workaround is to connect using 'localhost'.


据报道，当使用带有防火墙的 Kaspersky 7.0 时， H2 控制台在通过 IP 地址连接时非常慢。
一种解决方法是使用“本地主机”进行连接。

----

A small firewall is already built into the server:
other computers may not connect to the server by default.
To change this, go to 'Preferences' and select 'Allow connections from other computers'.


服务器中已经内置了一个小型防火墙：
默认情况下，其他计算机可能无法连接到服务器。
要更改此设置，请转到 `Preferences` 并选择 `Allow connections from other computers` 。

----

### Testing Java *Java 测试*

To find out which version of Java is installed, open a command prompt and type:


要找出安装的 Java 版本，请打开命令提示符并键入：

----

```shell
java -version
```

If you get an error message, you may need to add the Java binary directory to the path environment variable.


如果收到错误消息，则可能需要将 Java 二进制目录添加到路径环境变量中。

----

### Error Message 'Port may be in use' *错误消息 'Port may be in use'*

You can only start one instance of the H2 Console, otherwise you will get the following error message:
"The Web server could not be started. Possible cause: another server is already running...".
It is possible to start multiple console applications on the same computer (using different ports), but this is usually not required as the console supports multiple concurrent connections.


您只能启动一个 H2 Console 实例，否则您将收到以下错误信息：
"The Web server could not be started. Possible cause: another server is already running..."。
可以在同一台计算机上启动多个控制台应用程序（使用不同的端口），但这通常不是必需的，因为控制台支持多个并发连接。

----

### Using another Port *使用其他端口*

If the default port of the H2 Console is already in use by another application, then a different port needs to be configured.
The settings are stored in a properties file.
For details, see [Settings of the H2 Console](http://h2database.com/html/tutorial.html#console_settings).
The relevant entry is `webPort`.


如果 H2 Console 的默认端口已被其他应用程序使用，则需要配置不同的端口。
设置存储在属性文件中。
具体参见 [Settings of the H2 Console](./03%20Settings%20of%20the%20H2%20Console.md) 。
相关条目是 `webPort` 。

----

If no port is specified for the TCP and PG servers, each service will try to listen on its default port.
If the default port is already in use, a random port is used.


如果没有为 TCP 和 PG 服务器指定端口，则每个服务将尝试侦听其默认端口。
**如果默认端口已被使用，则使用随机端口**。

----

### Connecting to the Server using a Browser *使用浏览器连接到服务器*

If the server started successfully, you can connect to it using a web browser.
Javascript needs to be enabled.
If you started the server on the same computer as the browser, open the URL `http://localhost:8082`.
If you want to connect to the application from another computer, you need to provide the IP address of the server, for example: `http://192.168.0.2:8082`.
If you enabled TLS on the server side, the URL needs to start with `https://`.


如果服务器成功启动，您可以使用 Web 浏览器连接到它。
需要启用 Javascript 。
如果您在与浏览器相同的计算机上启动服务器，请打开 URL `http://localhost:8082` 。
如果要从另一台计算机连接到应用程序，则需要提供服务器的 IP 地址，例如： `http://192.168.0.2:8082` 。
如果您在服务器端启用了 TLS ，则 URL 需要以 `https:` 开头。

----

### Multiple Concurrent Sessions *多个并发会话*

Multiple concurrent browser sessions are supported.
As that the database objects reside on the server, the amount of concurrent work is limited by the memory available to the server application.


支持多个并发浏览器会话。
由于数据库对象驻留在服务器上，并发工作量受到服务器应用程序可用内存的限制。

----

### Login *登录*

At the login page, you need to provide connection information to connect to a database.
Set the JDBC driver class of your database, the JDBC URL, user name, and password.
If you are done, click [Connect].


在登录页面，您需要提供连接信息以连接到数据库。
设置数据库的 JDBC 驱动程序类、JDBC URL、用户名和密码。
完成后，单击 [Connect] 。

----

You can save and reuse previously saved settings.
The settings are stored in a properties file (see [Settings of the H2 Console](http://h2database.com/html/tutorial.html#console_settings)).


您可以保存和重复使用以前保存的设置。
这些设置存储在一个属性文件中（请参阅 [Settings of the H2 Console](./03%20Settings%20of%20the%20H2%20Console.md) ）。

----

### Error Messages *错误信息*

Error messages in are shown in red.
You can show/hide the stack trace of the exception by clicking on the message.


错误消息以红色显示。
您可以通过单击消息来显示/隐藏异常的堆栈跟踪。

----

### Adding Database Drivers *添加数据库驱动程序*

To register additional JDBC drivers (MySQL, PostgreSQL, HSQLDB,...), add the jar file names to the environment variables `H2DRIVERS` or `CLASSPATH`. 
Example (Windows):
to add the HSQLDB JDBC driver `C:\Programs\hsqldb\lib\hsqldb.jar`, set the environment variable `H2DRIVERS` to `C:\Programs\hsqldb\lib\hsqldb.jar`.


要注册其他 JDBC 驱动程序（MySQL、PostgreSQL、HSQLDB 等），请将 jar 文件名添加到环境变量 `H2DRIVERS` 或 `CLASSPATH` 。
示例（Windows）：
添加 HSQLDB JDBC 驱动 `C:\Programs\hsqldb\lib\hsqldb.jar` ，设置环境变量 `H2DRIVERS` 为 `C:\Programs\hsqldb\lib\hsqldb.jar` 。

----

Multiple drivers can be set; entries need to be separated by `;` (Windows) or `:` (other operating systems).
Spaces in the path names are supported.
The settings must not be quoted.


可设置多个驱动程序；条目需要用 `;` （Windows）或 `:` （其他操作系统）分隔。
支持路径名中的空格。
不得引用设置。

----

### Using the H2 Console *使用 H2 控制台*

The H2 Console application has three main panels: the toolbar on top, the tree on the left, and the query/result panel on the right.
The database objects (for example, tables) are listed on the left.
Type a SQL command in the query panel and click [Run].
The result appears just below the command.


H2 Console 应用程序具有三个主要面板：顶部的工具栏、左侧的树和右侧的查询结果面板。
数据库对象（例如，表）列在左侧。
在查询面板中键入 SQL 命令，然后单击 [Run] 。
结果显示在命令下方。

----

### Inserting Table Names or Column Names *插入表名或列名*

To insert table and column names into the script, click on the item in the tree.
If you click on a table while the query is empty, then `SELECT * FROM ...` is added.
While typing a query, the table that was used is expanded in the tree.
For example if you type `SELECT * FROM TEST T WHERE T.` then the table TEST is expanded.


要将表名和列名插入到脚本中，请单击树中的项目。
如果在查询为空时单击表，则会添加 `SELECT * FROM ...` 。
键入查询时，使用的表在树中展开。
例如，如果您键入 `SELECT * FROM TEST T WHERE T.` ，则表 TEST 将展开。

----

### Disconnecting and Stopping the Application *断开连接并停止应用程序*

To log out of the database, click [Disconnect] in the toolbar panel.
However, the server is still running and ready to accept new sessions.


要注销数据库，请单击工具栏面板中的 [Disconnect] 。
但是，服务器仍在运行并准备好接受新会话。

----

To stop the server, right click on the system tray icon and select [Exit].
If you don't have the system tray icon, navigate to [Preferences] and click [Shutdown], press [Ctrl]+[C] in the console where the server was started (Windows), or close the console window.


要停止服务器，请右键单击系统托盘图标并选择 [Exit] 。
如果您没有系统托盘图标，请导航至 [Preferences] 并单击 [Shutdown] ，在启动服务器的控制台 (Windows) 中按 [Ctrl]+[C] ，或关闭控制台窗口。

----
