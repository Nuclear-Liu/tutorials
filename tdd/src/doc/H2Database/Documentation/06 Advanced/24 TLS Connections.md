## TLS Connections *TLS 连接*

Remote TLS connections are supported using the Java Secure Socket Extension (`SSLServerSocket, SSLSocket`).
By default, anonymous TLS is enabled.


使用 Java 安全套接字扩展 (`SSLServerSocket, SSLSocket`) 支持远程 TLS 连接。
默认情况下，启用匿名 TLS。

---

To use your own keystore, set the system properties `javax.net.ssl.keyStore` and `javax.net.ssl.keyStorePassword` before starting the H2 server and client.
See also [Customizing the Default Key and Trust Stores, Store Types, and Store Passwords]() for more information.


要使用您自己的密钥库，请在启动 H2 服务器和客户端之前设置系统属性 `javax.net.ssl.keyStore` 和 `javax.net.ssl.keyStorePassword`。
有关详细信息，另请参阅 [Customizing the Default Key and Trust Stores, Store Types, and Store Passwords]() 。

---

To disable anonymous TLS, set the system property `h2.enableAnonymousTLS` to false.


要禁用匿名 TLS，请将系统属性 `h2.enableAnonymousTLS` 设置为 false。

---
