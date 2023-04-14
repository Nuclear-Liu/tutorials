## Protection against Remote Access *防止远程访问*

By default this database does not allow connections from other machines when starting the H2 Console, the TCP server, or the PG server.
Remote access can be enabled using the command line options `-webAllowOthers`, `-tcpAllowOthers`, `-pgAllowOthers`.


默认情况下，此数据库在启动 H2 控制台、TCP 服务器或 PG 服务器时不允许来自其他机器的连接。
可以使用命令行选项 `-webAllowOthers`、`-tcpAllowOthers`、`-pgAllowOthers` 启用远程访问。

---

If you enable remote access using `-tcpAllowOthers` or `-pgAllowOthers`, please also consider using the options `-baseDir`, so that remote users can not create new databases or access existing databases with weak passwords.
When using the option `-baseDir`, only databases within that directory may be accessed.
Ensure the existing accessible databases are protected using strong passwords.


如果您使用 `-tcpAllowOthers` 或 `-pgAllowOthers` 启用远程访问，还请考虑使用选项 `-baseDir` ，这样远程用户就无法使用弱密码创建新数据库或访问现有数据库。
使用选项 `-baseDir` 时，只能访问该目录中的数据库。
确保使用强密码保护现有的可访问数据库。

---

If you enable remote access using `-webAllowOthers`, please ensure the web server can only be accessed from trusted networks.
The options `-baseDir` don't protect access to the tools section, prevent remote shutdown of the web server, changes to the preferences, the saved connection settings, or access to other databases accessible from the system.


如果您使用 `-webAllowOthers` 启用远程访问，请确保只能从受信任的网络访问 Web 服务器。
选项 `-baseDir` 不保护对工具部分的访问、防止远程关闭Web 服务器、更改首选项、保存的连接设置或访问可从系统访问的其他数据库。

---
