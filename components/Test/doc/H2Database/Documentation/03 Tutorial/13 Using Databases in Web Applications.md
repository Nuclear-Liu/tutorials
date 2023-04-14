## Using Databases in Web Applications *在 Web 应用程序中使用数据库*

There are multiple ways to access a database from within web applications.
Here are some examples if you use Tomcat or JBoss.


有多种方法可以从 Web 应用程序中访问数据库。
如果您使用 Tomcat 或 JBoss，这里有一些示例。

----

### Embedded Mode *嵌入式模式*

The (currently) simplest solution is to use the database in the embedded mode, that means open a connection in your application when it starts (a good solution is using a Servlet Listener, see below), or when a session starts.
A database can be accessed from multiple sessions and applications at the same time, as long as they run in the same process.
Most Servlet Containers (for example Tomcat) are just using one process, so this is not a problem (unless you run Tomcat in clustered mode).
Tomcat uses multiple threads and multiple classloaders.
If multiple applications access the same database at the same time, you need to put the database jar in the `shared/lib` or `server/lib` directory.
It is a good idea to open the database when the web application starts, and close it when the web application stops.
If using multiple applications, only one (any) of them needs to do that.
In the application, an idea is to use one connection per Session, or even one connection per request (action).
Those connections should be closed after use if possible (but it's not that bad if they don't get closed).


（目前）最简单的解决方案是在嵌入式模式下使用数据库，这意味着在应用程序启动时（一个好的解决方案是使用 Servlet 侦听器，见下文）或会话启动时在应用程序中打开连接。
可以从多个会话和应用程序同时访问数据库，只要它们在同一进程中运行即可。
大多数 Servlet 容器（例如 Tomcat）只使用一个进程，所以这不是问题（除非您在集群模式下运行 Tomcat）。
Tomcat 使用多个线程和多个类加载器。
如果多个应用同时访问同一个数据库，则需要将数据库 jar 放在 `shared/lib` 或 `server/lib` 目录下。
最好在 Web 应用程序启动时打开数据库，并在 Web 应用程序停止时关闭它。
如果使用多个应用程序，则只有其中一个（任何）需要这样做。
在应用程序中，一个想法是每个会话使用一个连接，甚至每个请求（操作）使用一个连接。
如果可能的话，这些连接应该在使用后关闭（但如果它们没有关闭也没有那么糟糕）。

----

### Server Mode *服务器模式*

The server mode is similar, but it allows you to run the server in another process.


服务器模式类似，但它允许您在另一个进程中运行服务器。

----

### Using a Servlet Listener to Start and Stop a Database *使用 Servlet 侦听器启动和停止数据库*

Add the h2*.jar file to your web application, and add the following snippet to your web.xml file (between the `context-param` and the `filter` section):


将 h2.jar 文件添加到您的 Web 应用程序，并将以下代码段添加到您的 web.xml 文件（在 `context-param` 和 `filter` 部分之间）：

----

```xml
<listener>
    <listener-class>org.h2.server.web.DbStarter</listener-class>
</listener>
```

For details on how to access the database, see the file `DbStarter.java`.
By default this tool opens an embedded connection using the database URL `jdbc:h2:~/test`, user name `sa`, and password `sa`.
If you want to use this connection within your servlet, you can access as follows:


有关如何访问数据库的详细信息，请参阅文件 `DbStarter.java` 。
默认情况下，此工具使用数据库 URL `jdbc:h2:~test` 、用户名 `sa` 和密码 `sa` 打开嵌入式连接。
如果要在 servlet 中使用此连接，可以按如下方式访问：

----

```
Connection conn = getServletContext().getAttribute("connection");
```

`DbStarter` can also start the TCP server, however this is disabled by default.
To enable it, use the parameter `db.tcpServer` in the file `web.xml`.
Here is the complete list of options.
These options need to be placed between the `description` tag and the `listener` / `filter` tags:


`DbStarter` 也可以启动 TCP 服务器，但默认情况下这是禁用的。
要启用它，请使用文件 `web.xml` 中的参数 `db.tcpServer` 。
这是完整的选项列表。
这些选项需要放在 `description` 标签和 `listener` / `filter` 标签之间：

----

```xml
<context-param>
    <param-name>db.url</param-name>
    <param-value>jdbc:h2:~/test</param-value>
</context-param>
<context-param>
    <param-name>db.user</param-name>
    <param-value>sa</param-value>
</context-param>
<context-param>
    <param-name>db.password</param-name>
    <param-value>sa</param-value>
</context-param>
<context-param>
    <param-name>db.tcpServer</param-name>
    <param-value>-tcpAllowOthers</param-value>
</context-param>
```

When the web application is stopped, the database connection will be closed automatically.
If the TCP server is started within the `DbStarter`, it will also be stopped automatically.


当 Web 应用程序停止时，数据库连接将自动关闭。
如果 TCP 服务器在 `DbStarter` 中启动，它也会自动停止。

----

### Using the H2 Console Servlet *使用 H2 控制台 Servlet*

The H2 Console is a standalone application and includes its own web server, but it can be used as a servlet as well.
To do that, include the `h2*.jar` file in your application, and add the following configuration to your `web.xml`:


H2 控制台是一个独立的应用程序，包括它自己的 Web 服务器，但它也可以用作 servlet。
为此，请在您的应用程序中包含 `h2.jar` 文件，并将以下配置添加到您的 `web.xml` 中：

----

```xml
<servlet>
    <servlet-name>H2Console</servlet-name>
    <servlet-class>org.h2.server.web.WebServlet</servlet-class>
    <!--
    <init-param>
        <param-name>webAllowOthers</param-name>
        <param-value></param-value>
    </init-param>
    <init-param>
        <param-name>trace</param-name>
        <param-value></param-value>
    </init-param>
    -->
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>H2Console</servlet-name>
    <url-pattern>/console/*</url-pattern>
</servlet-mapping>
<!--
<security-role>
    <role-name>admin</role-name>
</security-role>
<security-constraint>
    <web-resource-collection>
        <web-resource-name>H2 Console</web-resource-name>
        <url-pattern>/console/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>admin</role-name>
    </auth-constraint>
</security-constraint>
-->
```

For details, see also `src/tools/WEB-INF/web.xml`.


有关详细信息，另请参阅 `src/tools/WEB-INF/web.xml` 。

----

To create a web application with just the H2 Console, run the following command:


要仅使用 H2 控制台创建 Web 应用程序，请运行以下命令：

----

```shell
build warConsole
```

---
