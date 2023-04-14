## Using the Server *使用服务器*

H2 currently supports three server: a web server (for the H2 Console), a TCP server (for client/server connections) and an PG server (for PostgreSQL clients).
Please note that only the web server supports browser connections. The servers can be started in different ways, one is using the `Server` tool.
Starting the server doesn't open a database - databases are opened as soon as a client connects.


H2 目前支持三个服务器：一个 Web 服务器（用于 H2 控制台）、一个 TCP 服务器（用于客户端服务器连接）和一个 PG 服务器（用于 PostgreSQL 客户端）。
请注意，只有 Web 服务器支持浏览器连接。服务器可以通过不同的方式启动，一种是使用 `Server` 工具。
启动服务器不会打开数据库 - 一旦客户端连接，数据库就会打开。

----

### Starting the Server Tool from Command Line *从命令行启动服务器工具*

To start the `Server` tool from the command line with the default settings, run:


要使用默认设置从命令行启动 `Server` 工具，请运行：

----

```shell
java -cp h2*.jar org.h2.tools.Server
```

This will start the tool with the default options. 
To get the list of options and default values, run:


这将使用默认选项启动该工具。
要获取选项和默认值列表，请运行：

----

```shell
java -cp h2*.jar org.h2.tools.Server -?
```

There are options available to use other ports, and start or not start parts.


可以选择使用其他端口，以及启动或不启动部件。

----

### Connecting to the TCP Server *连接到 TCP 服务器*

To remotely connect to a database using the TCP server, use the following driver and database URL:


要使用 TCP 服务器远程连接到数据库，请使用以下驱动程序和数据库 URL：

----

* JDBC driver class: `org.h2.Driver`
* Database URL: `jdbc:h2:tcp://localhost/~/test `


* JDBC 驱动程序类：`org.h2.Driver`
* 数据库 URL： `jdbc:h2:tcp:localhost~test `

----

For details about the database URL, see also in Features.
Please note that you can't connection with a web browser to this URL.
You can only connect using a H2 client (over JDBC).


有关数据库 URL 的详细信息，另请参阅功能。
请注意，您无法通过网络浏览器连接到此 URL。
您只能使用 H2 客户端（通过 JDBC）进行连接。

----

### Starting the TCP Server within an Application *在应用程序中启动 TCP 服务器*

Servers can also be started and stopped from within an application. 
Sample code:


服务器也可以从应用程序中启动和停止。
示例代码：

----

```
import org.h2.tools.Server;
 ...
// start the TCP Server
Server server = Server.createTcpServer(args).start();
 ...
// stop the TCP Server
server.stop();
```

### Stopping a TCP Server from Another Process *从另一个进程将 TCP 服务器置顶*

The TCP server can be stopped from another process. 
To stop the server from the command line, run:


TCP 服务器可以被另一个进程停止。
要从命令行停止服务器，请运行：

----

```shell
java org.h2.tools.Server -tcpShutdown tcp://localhost:9092 -tcpPassword password
```

To stop the server from a user application, use the following code:


要从用户应用程序停止服务器，请使用以下代码：

----

```
org.h2.tools.Server.shutdownTcpServer("tcp://localhost:9092", "password", false, false);
```

This function will only stop the TCP server. 
If other server were started in the same process, they will continue to run.
To avoid recovery when the databases are opened the next time, all connections to the databases should be closed before calling this method.
To stop a remote server, remote connections must be enabled on the server.
Shutting down a TCP server is protected using the option `-tcpPassword` (the same password must be used to start and stop the TCP server).


该函数只会停止 TCP 服务器。
如果其他服务器在同一进程中启动，它们将继续运行。
为避免下次打开数据库时恢复，在**调用此方法之前应关闭与数据库的所有连接**。
要停止远程服务器，必须在服务器上启用远程连接。
使用选项 `-tcpPassword` 可以保护关闭 TCP 服务器（必须使用相同的密码来启动和停止 TCP 服务器）。

----
