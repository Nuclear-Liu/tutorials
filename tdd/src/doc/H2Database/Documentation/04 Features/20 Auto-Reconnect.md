## Auto-Reconnect *自动重连*

The auto-reconnect feature causes the JDBC driver to reconnect to the database if the connection is lost.
The automatic re-connect only occurs when auto-commit is enabled; if auto-commit is disabled, an exception is thrown.
To enable this mode, append `;AUTO_RECONNECT=TRUE` to the database URL.


如果连接丢失，自动重新连接功能会导致 JDBC 驱动程序重新连接到数据库。
自动重新连接仅在启用自动提交时发生；如果禁用自动提交，则会引发异常。
要启用此模式，请将 `;AUTO_RECONNECT=TRUE` 附加到数据库 URL。

---

Re-connecting will open a new session.
After an automatic re-connect, variables and local temporary tables definitions (excluding data) are re-created.
The contents of the system table `INFORMATION_SCHEMA.SESSION_STATE` contains all client side state that is re-created.


重新连接将打开一个新会话。
自动重新连接后，将重新创建变量和本地临时表定义（不包括数据）。
系统表 `INFORMATION_SCHEMA.SESSION_STATE` 的内容包含所有重新创建的客户端状态。

---

If another connection uses the database in exclusive mode (enabled using `SET EXCLUSIVE 1` or `SET EXCLUSIVE 2`), then this connection will try to re-connect until the exclusive mode ends. 


如果另一个连接以独占模式使用数据库（使用 `SET EXCLUSIVE 1` 或 `SET EXCLUSIVE 2` 启用），则此连接将尝试重新连接，直到独占模式结束。

---
