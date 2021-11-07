## ODBC Driver *ODBC 驱动*

This database does not come with its own ODBC driver at this time, but it supports the PostgreSQL network protocol.
Therefore, the PostgreSQL ODBC driver can be used.
Support for the PostgreSQL network protocol is quite new and should be viewed as experimental.
It should not be used for production applications.


该数据库目前没有自带 ODBC 驱动，但支持 PostgreSQL 网络协议。
因此，可以使用 PostgreSQL ODBC 驱动程序。
对 PostgreSQL 网络协议的支持是相当新的，应该被视为实验性的。
它不应用于生产应用程序。

---

To use the PostgreSQL ODBC driver on 64 bit versions of Windows, first run `c:/windows/syswow64/odbcad32.exe`.
At this point you set up your DSN just like you would on any other system.
See also: Re: [ODBC Driver on Windows 64 bit]()


要在 64 位版本的 Windows 上使用 PostgreSQL ODBC 驱动程序，首先运行 `c:windowssyswow64odbcad32.exe` 。
此时，您可以像在任何其他系统上一样设置 DSN。
另请参阅：Re：[Windows 64 位上的 ODBC 驱动程序]()

---

### ODBC Installation *ODBC 安装*

First, the ODBC driver must be installed.
Any recent PostgreSQL ODBC driver should work, however version 8.2 (`psqlodbc-08_02*`) or newer is recommended.
The Windows version of the PostgreSQL ODBC driver is available at [https://www.postgresql.org/ftp/odbc/versions/msi/]().


首先，必须安装 ODBC 驱动程序。
任何最新的 PostgreSQL ODBC 驱动程序都应该可以工作，但建议使用 8.2 (`psqlodbc-08_02*`) 或更新版本。
PostgreSQL ODBC 驱动程序的 Windows 版本可从 [https:www.postgresql.orgftpodbcversionsmsi]() 获得。

---

### Starting the Server *启动服务器*

After installing the ODBC driver, start the H2 Server using the command line:


安装好 ODBC 驱动后，使用命令行启动 H2 Server：

---

```shell
java -cp h2*.jar org.h2.tools.Server
```

The PG Server (PG for PostgreSQL protocol) is started as well.
By default, databases are stored in the current working directory where the server is started. 
Use `-baseDir` to save databases in another directory, for example the user home directory:


PG 服务器（PG for PostgreSQL 协议）也已启动。
默认情况下，数据库存储在服务器启动的当前工作目录中。
使用 `-baseDir` 将数据库保存在另一个目录中，例如用户主目录：

---

```shell
java -cp h2*.jar org.h2.tools.Server -baseDir ~
```

The PG server can be started and stopped from within a Java application as follows:


PG 服务器可以从 Java 应用程序中启动和停止，如下所示：

---

```
Server server = Server.createPgServer("-baseDir", "~");
server.start();
...
server.stop();
```

By default, only connections from localhost are allowed.
To allow remote connections, use `-pgAllowOthers` when starting the server.


默认情况下，只允许来自 localhost 的连接。
要允许远程连接，请在启动服务器时使用 `-pgAllowOthers` 。

---

To map an ODBC database name to a different JDBC database name, use the option `-key` when starting the server.
Please note only one mapping is allowed.
The following will map the ODBC database named `TEST` to the database URL `jdbc:h2:~/data/test;cipher=aes`:


要将 ODBC 数据库名称映射到不同的 JDBC 数据库名称，请在启动服务器时使用选项 `-key`。
请注意，只允许一种映射。
下面将把名为“TEST”的 ODBC 数据库映射到数据库 URL `jdbc:h2:~/data/test;cipher=aes` ：

---

```shell
java org.h2.tools.Server -pg -key TEST "~/data/test;cipher=aes"
```

### ODBC Configuration *ODBC 配置*

After installing the driver, a new Data Source must be added.
In Windows, run `odbcad32.exe` to open the Data Source Administrator.
Then click on 'Add...' and select the PostgreSQL Unicode driver.
Then click 'Finish'.
You will be able to change the connection properties.
The property column represents the property key in the `odbc.ini` file (which may be different from the GUI).


安装驱动程序后，必须添加新的数据源。
在 Windows 中，运行 `odbcad32.exe` 以打开数据源管理器。
然后单击 'Add...' 并选择 PostgreSQL Unicode 驱动程序。
然后单击 'Finish' 。
您将能够更改连接属性。
属性列表示 `odbc.ini` 文件中的属性键（可能与GUI 不同）。

---

| Property | Example | Remarks |
| ---- | ---- | ---- |
| Data Source | H2 Test | The name of the ODBC Data Source |
| Database | ~/test;ifexists=true | The database name. This can include connections settings. By default, the database is stored in the current working directory where the Server is started except when the -baseDir setting is used. The name must be at least 3 characters.  |
| Servername | localhost | The server name or IP address. By default, only remote connections are allowed |
| Username | sa | The database user name. |
| SSL | false (disabled) | At this time, SSL is not supported. |
| Port | 5435 | The port where the PG Server is listening. |
| Password | sa | The database password. |


| 属性 | 例子 | 评论 |
| ---- | ---- | ---- |
| Data Source | H2 Test | The name of the ODBC Data Source |
| Database | ~/test;ifexists=true | The database name. This can include connections settings. By default, the database is stored in the current working directory where the Server is started except when the -baseDir setting is used. The name must be at least 3 characters.  |
| Servername | localhost | The server name or IP address. By default, only remote connections are allowed |
| Username | sa | The database user name. |
| SSL | false (disabled) | At this time, SSL is not supported. |
| Port | 5435 | The port where the PG Server is listening. |
| Password | sa | The database password. |

---

To improve performance, please enable 'server side prepare' under Options / Datasource / Page 2 / Server side prepare.


为了提高性能，请在选项数据源页面 2 服务器端准备下启用“服务器端准备”。

---

Afterwards, you may use this data source.


之后，您可以使用此数据源。

---

### PG Protocol Support Limitations *PG 协议支持限制*

At this time, only a subset of the PostgreSQL network protocol is implemented. 
Also, there may be compatibility problems on the SQL level, with the catalog, or with text encoding. 
Problems are fixed as they are found. 
Currently, statements can not be canceled when using the PG protocol. 
Also, H2 does not provide index meta over ODBC.


目前，只实现了 PostgreSQL 网络协议的一个子集。
此外，在 SQL 级别、目录或文本编码方面可能存在兼容性问题。
发现问题后就解决了。
目前，使用 PG 协议时不能取消语句。
此外，H2 不通过 ODBC 提供索引元。

---

PostgreSQL ODBC Driver Setup requires a database password; that means it is not possible to connect to H2 databases without password.
This is a limitation of the ODBC driver.


PostgreSQL ODBC Driver Setup 需要数据库密码；这意味着没有密码就无法连接到 H2 数据库。
这是 ODBC 驱动程序的限制。

---

### Security Considerations *安全注意事项*

Currently, the PG Server does not support challenge response or encrypt passwords.
This may be a problem if an attacker can listen to the data transferred between the ODBC driver and the server, because the password is readable to the attacker.
Also, it is currently not possible to use encrypted SSL connections.
Therefore the ODBC driver should not be used where security is important.


目前，PG Server 不支持挑战响应或加密密码。
如果攻击者可以侦听 ODBC 驱动程序和服务器之间传输的数据，这可能是一个问题，因为攻击者可以读取密码。
此外，目前无法使用加密的 SSL 连接。
因此，在安全性很重要的情况下不应使用 ODBC 驱动程序。

---

The first connection that opens a database using the PostgreSQL server needs to be an administrator user.
Subsequent connections don't need to be opened by an administrator.


使用 PostgreSQL 服务器打开数据库的第一个连接需要是管理员用户。
后续连接不需要由管理员打开。

---

### Using Microsoft Access *使用 Microsoft Access*

When using Microsoft Access to edit data in a linked H2 table, you may need to enable the following option: 
Tools - Options - Edit/Find - ODBC fields.


使用 Microsoft Access 编辑链接 H2 表中的数据时，您可能需要启用以下选项：
工具 - 选项 - Edit/Find - ODBC 字段。

---
