## Creating New Databases *创建新数据库*

By default, if the database specified in the [embedded](http://h2database.com/html/features.html#database_url) URL does not yet exist, a new (empty) database is created automatically.
The user that created the database automatically becomes the administrator of this database.


默认情况下，如果 [embedded]() URL 中指定的数据库尚不存在，则会**自动创建**一个新的（空）数据库。
创建数据库的用户自动成为该数据库的管理员。

----

Auto-creation of databases can be disabled, see [Opening a Database Only if it Already Exists](http://h2database.com/html/features.html#database_only_if_exists).


可以禁用自动创建数据库，请参阅 [Opening a Database Only if it Already Exists]() 。

----

H2 Console does not allow creation of databases unless a browser window is opened by Console during its startup or from its icon in the system tray and remote access is not enabled.
A context menu of the tray icon can also be used to create a new database.


H2 Console 不允许创建数据库，除非 Console 在启动期间或从系统托盘中的图标打开浏览器窗口，并且未启用远程访问。
托盘图标的上下文菜单也可用于创建新数据库。

----

You can also create a new local database from a command line with a Shell tool:


您还可以使用 Shell 工具从命令行创建新的本地数据库：

----

```shell
> java -cp h2-*.jar org.h2.tools.Shell

Welcome to H2 Shell
Exit with Ctrl+C
[Enter]   jdbc:h2:mem:2
URL       jdbc:h2:./path/to/database
[Enter]   org.h2.Driver
Driver
[Enter]   sa
User      your_username
Password  (hidden)
Type the same password again to confirm database creation.
Password  (hidden)
Connected

sql> quit
Connection closed
```

By default remote creation of databases from a TCP connection or a web interface is not allowed.
It's not recommended to enable remote creation of databases due to security reasons.
User who creates a new database becomes its administrator and therefore gets the same access to your JVM as H2 has and the same access to your operating system as Java and your system account allows.
It's recommended to create all databases locally using an embedded URL, local H2 Console, or the Shell tool.


默认情况下，不允许从 TCP 连接或 Web 界面远程创建数据库。
出于安全原因，不建议启用远程创建数据库。
创建新数据库的用户将成为其管理员，因此可以获得与 H2 相同的对您的 JVM 的访问权限，以及与 Java 和您的系统帐户允许的对您的操作系统相同的访问权限。
建议使用嵌入式 URL 、本地 H2 控制台或 Shell 工具在本地创建所有数据库。

----

If you really need to allow remote database creation, you can pass `-ifNotExists` parameter to TCP, PG, or Web servers (but not to the Console tool).
Its combination with `-tcpAllowOthers`, `-pgAllowOthers`, or `-webAllowOthers` effectively creates a remote security hole in your system, if you use it, always guard your ports with a firewall or some other solution and use such combination of settings only in trusted networks.


如果您确实需要允许远程创建数据库，您可以将 `-ifNotExists` 参数传递给 TCP、PG 或 Web 服务器（但不能传递给控制台工具）。
它与 `-tcpAllowOthers` 、`-pgAllowOthers` 或 `-webAllowOthers` 的组合有效地在您的系统中创建了一个远程安全漏洞，如果您使用它，请始终使用防火墙或其他解决方案保护您的端口并使用此类设置组合仅在受信任的网络中。

----

H2 Servlet also supports such option.
When you use it always protect the servlet with security constraints, see [Using the H2 Console Servlet](http://h2database.com/html/tutorial.html#usingH2ConsoleServlet) for example;
don't forget to uncomment and adjust security configuration for your needs.


H2 Servlet 也支持这样的选项。
当你使用它时总是用安全约束保护servlet，例如参见 [Using the H2 Console Servlet]() ；
不要忘记取消注释并根据您的需要调整安全配置。

----
