# Reliable Messaging _可靠消息传递_

在 WebSocket STOMP 中默认自动确认消息，即接受消息即确定消费。

> 客户端消息回调函数触发异常，默认自动确定消息，消息丢失。

**可靠消息推送**：
* Heard 添加手动 `ack` 确认: `ack: 'client'` ，消息处理完成发送确认: `response.ack()`
* 使用持久化队列
