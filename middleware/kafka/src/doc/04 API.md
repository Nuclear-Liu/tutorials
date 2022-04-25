# API

kafka 持久化数据MQ 数据以 byte[] 做交互；双方需要约定编解码；
可以使用零拷贝 `sendfile` 系统调用实现快速数据消费；

Producer 面向 Broker ，虽然期望面向的是 Topic ；

## 

