# WebSocket:STOMP

**STOMP Over WebSocket:**

| App     | WebSocket | STOMP Over WebSocket |
|---------|-----------|----------------------|
| 点对点发送   |           |                      |
| 广播发送    |           |                      |
| 业务主题    |           |                      |
| 负载均衡    |           |                      |
| 兼容性     |           |                      |
| 心跳检测    |           |                      |
| 消息确认、重发 |           |                      |
| 事务控制    |           |                      |


## Broker Configuration

> 默认在不配置 Broker 时，订阅主题可以随意，当自定义配置后，必须时 `enableSimpleBroker` 中指定的主题


* `enableSimpleBroker()` 设置 Broker 的主题(**基于内存代理**)
  * `/topic/` 广播模式
  * `/queue/` 点对点模式
* `setUserDestinationPrefix` 修改默认用户前缀(**默认 `user` **)
* `setApplicationDestinationPrefixes` 修改请求应用主题统一前缀
