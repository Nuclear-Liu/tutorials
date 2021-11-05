## OSGi *OSGi*

The standard H2 jar can be dropped in as a bundle in an OSGi container.
H2 implements the JDBC Service defined in OSGi Service Platform Release 4 Version 4.2 Enterprise Specification.
The H2 Data Source Factory service is registered with the following properties: `OSGI_JDBC_DRIVER_CLASS=org.h2.Driver` and `OSGI_JDBC_DRIVER_NAME=H2 JDBC Driver`.
The `OSGI_JDBC_DRIVER_VERSION` property reflects the version of the driver as is.


标准 H2 jar 可以作为一个包放入 OSGi 容器中。
H2 实现了 OSGi Service Platform Release 4 Version 4.2 Enterprise Specification 中定义的 JDBC Service。
H2 数据源工厂服务注册了以下属性：`OSGI_JDBC_DRIVER_CLASS=org.h2.Driver` 和 `OSGI_JDBC_DRIVER_NAME=H2 JDBC Driver` 。
`OSGI_JDBC_DRIVER_VERSION` 属性反映了驱动程序的版本。

---

The following standard configuration properties are supported: `JDBC_USER, JDBC_PASSWORD, JDBC_DESCRIPTION, JDBC_DATASOURCE_NAME, JDBC_NETWORK_PROTOCOL, JDBC_URL, JDBC_SERVER_NAME, JDBC_PORT_NUMBER`.
Any other standard property will be rejected.
Non-standard properties will be passed on to H2 in the connection URL.


支持以下标准配置属性： `JDBC_USER, JDBC_PASSWORD, JDBC_DESCRIPTION, JDBC_DATASOURCE_NAME, JDBC_NETWORK_PROTOCOL, JDBC_URL, JDBC_SERVER_NAME, JDBC_PORT_NUMBER` 。
任何其他标准属性都将被拒绝。
非标准属性将在连接 URL 中传递给 H2。

---
