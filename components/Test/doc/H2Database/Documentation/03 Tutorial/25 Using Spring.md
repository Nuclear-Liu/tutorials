## Using Spring *使用 Spring*

### Using the TCP Server *使用 TCP 服务器*

Use the following configuration to start and stop the H2 TCP server using the Spring Framework:


使用以下配置使用 Spring Framework 启动和停止 H2 TCP 服务器：

---

```xml
<bean id = "org.h2.tools.Server"
            class="org.h2.tools.Server"
            factory-method="createTcpServer"
            init-method="start"
            destroy-method="stop">
    <constructor-arg value="-tcp,-tcpAllowOthers,-tcpPort,8043" />
</bean>
```

The `destroy-method` will help prevent exceptions on hot-redeployment or when restarting the server.


`destroy-method` 将有助于防止热重新部署或重新启动服务器时出现异常。

---
