## Settings of the H2 Console *H2 控制台的设置*

The settings of the H2 Console are stored in a configuration file called `.h2.server.properties` in you user home directory.
For Windows installations, the user home directory is usually `C:\Documents and Settings\[username]` or `C:\Users\[username]`.
The configuration file contains the settings of the application and is automatically created when the H2 Console is first started. 
Supported settings are:


H2 控制台的设置存储在用户主目录中名为 `.h2.server.properties` 的配置文件中。
对于 Windows 安装，用户主目录通常是 `C:\Documents and Settings\[username]` 或 `C:\Users\[username]` 。
配置文件包含应用程序的设置，并在 H2 Console 首次启动时自动创建。
支持的设置有：

----

* `webAllowOthers`: allow other computers to connect.
* `webPort`: the port of the H2 Console
* `webSSL`: use encrypted TLS (HTTPS) connections.
* `webAdminPassword`: password to access preferences and tools of H2 Console.


* `webAllowOthers` ：允许其他计算机连接。
* `webPort` ：H2 控制台的端口
* `webSSL` ：使用加密的 TLS (HTTPS) 连接。
* `webAdminPassword` ：访问 H2 控制台首选项和工具的密码。

----

In addition to those settings, the properties of the last recently used connection are listed in the form <number>=<name>|<driver>|<url>|<user> using the escape character `\`.
Example: `1=Generic H2 (Embedded)|org.h2.Driver|jdbc\:h2\:~/test|sa`


除了这些设置之外，最近使用的连接的属性以 <number>=<name>|<driver>|<url>|<user> 的形式使用转义字符 `\` 列出。

----
