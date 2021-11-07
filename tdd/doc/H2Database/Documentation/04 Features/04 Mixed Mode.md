## Mixed Mode *混合模式*

The mixed mode is a combination of the embedded and the server mode.
The first application that connects to a database does that in embedded mode, but also starts a server so that other applications (running in different processes or virtual machines) can concurrently access the same data.
The local connections are as fast as if the database is used in just the embedded mode, while the remote connections are a bit slower.


混合模式是嵌入式和服务器模式的结合。
连接到数据库的第一个应用程序以嵌入式模式执行此操作，但也会启动服务器，以便其他应用程序（在不同的进程或虚拟机中运行）可以同时访问相同的数据。
本地连接的速度与仅在嵌入模式下使用数据库一样快，而远程连接的速度稍慢。

---

The server can be started and stopped from within the application (using the server API), or automatically (automatic mixed mode).
When using the [automatic mixed mode](https://h2database.com/html/features.html#auto_mixed_mode), all clients that want to connect to the database (no matter if it's an local or remote connection) can do so using the exact same database URL.


可以从应用程序内部（使用服务器 API）或自动（自动混合模式）启动和停止服务器。
使用 [automatic mixed mode]() 时，所有想要连接到数据库的客户端（无论是本地连接还是远程连接）都可以使用完全相同的数据库 URL 进行连接。

---

![connection-mode-mixed-2](connection-mode-mixed-2.png)
