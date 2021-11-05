## Setting the Server Bind Address *设置服务器绑定地址*

Usually server sockets accept connections on any/all local addresses.
This may be a problem on multi-homed hosts.
To bind only to one address, use the system property `h2.bindAddress`.
This setting is used for both regular server sockets and for TLS server sockets.
IPv4 and IPv6 address formats are supported.


通常服务器套接字接受任何本地地址上的连接。
这可能是多宿主主机上的问题。
要仅绑定到一个地址，请使用系统属性 `h2.bindAddress` 。
此设置用于常规服务器套接字和 TLS 服务器套接字。
支持 IPv4 和 IPv6 地址格式。

---
