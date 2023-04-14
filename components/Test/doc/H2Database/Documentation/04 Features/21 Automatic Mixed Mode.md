## Automatic Mixed Mode *自动混合模式*

Multiple processes can access the same database without having to start the server manually.
To do that, append `;AUTO_SERVER=TRUE` to the database URL.
You can use the same database URL independent of whether the database is already open or not.
This feature doesn't work with in-memory databases. Example database URL:


多个进程可以访问同一个数据库，而无需手动启动服务器。
为此，请将 `;AUTO_SERVER=TRUE` 附加到数据库 URL。
无论数据库是否已打开，您都可以使用相同的数据库 URL。
此功能不适用于内存数据库。示例数据库 URL：

---

`jdbc:h2:/data/test;AUTO_SERVER=TRUE`

Use the same URL for all connections to this database.
Internally, when using this mode, the first connection to the database is made in embedded mode, and additionally a server is started internally (as a daemon thread).
If the database is already open in another process, the server mode is used automatically.
The IP address and port of the server are stored in the file `.lock.db`, that's why in-memory databases can't be supported.


对该数据库的所有连接使用相同的 URL。
在内部，当使用这种模式时，到数据库的第一个连接是在嵌入式模式下进行的，另外还有一个服务器在内部启动（作为一个守护线程）。
如果数据库已经在另一个进程中打开，则自动使用服务器模式。
服务器的IP地址和端口存储在文件`.lock.db`中，这就是不能支持内存数据库的原因。

---

The application that opens the first connection to the database uses the embedded mode, which is faster than the server mode.
Therefore the main application should open the database first if possible.
The first connection automatically starts a server on a random port.
This server allows remote connections, however only to this database (to ensure that, the client reads `.lock.db` file and sends the random key that is stored there to the server).
When the first connection is closed, the server stops.
If other (remote) connections are still open, one of them will then start a server (auto-reconnect is enabled automatically).


打开第一个数据库连接的应用程序使用嵌入式模式，比服务器模式更快。
因此，如果可能，主应用程序应首先打开数据库。
第一个连接会自动在随机端口上启动服务器。
该服务器允许远程连接，但仅限于该数据库（以确保客户端读取 `.lock.db` 文件并将存储在那里的随机密钥发送到服务器）。
当第一个连接关闭时，服务器停止。
如果其他（远程）连接仍然打开，其中一个将启动服务器（自动重新连接自动启用）。

---

All processes need to have access to the database files.
If the first connection is closed (the connection that started the server), open transactions of other connections will be rolled back (this may not be a problem if you don't disable autocommit).
Explicit client/server connections (using `jdbc:h2:tcp://` or `ssl://`) are not supported.
This mode is not supported for in-memory databases.


所有进程都需要访问数据库文件。
如果第一个连接关闭（启动服务器的连接），其他连接的打开事务将被回滚（如果不禁用自动提交，这可能不是问题）。
不支持显式客户端服务器连接（使用 `jdbc:h2:tcp://` 或 `ssl://` ）。
内存数据库不支持此模式。

---

Here is an example how to use this mode.
Application 1 and 2 are not necessarily started on the same computer, but they need to have access to the database files.
Application 1 and 2 are typically two different processes (however they could run within the same process).


以下是如何使用此模式的示例。
应用程序 1 和 2 不一定在同一台计算机上启动，但它们需要能够访问数据库文件。
应用程序 1 和 2 通常是两个不同的进程（但它们可以在同一个进程中运行）。

---

```
// Application 1:
DriverManager.getConnection("jdbc:h2:/data/test;AUTO_SERVER=TRUE");

// Application 2:
DriverManager.getConnection("jdbc:h2:/data/test;AUTO_SERVER=TRUE");
```

When using this feature, by default the server uses any free TCP port.
The port can be set manually using `AUTO_SERVER_PORT=9090`. 


使用此功能时，默认情况下服务器使用任何可用的 TCP 端口。
可以使用 `AUTO_SERVER_PORT=9090` 手动设置端口。

---
