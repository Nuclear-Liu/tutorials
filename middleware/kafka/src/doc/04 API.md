# API

kafka 持久化数据MQ 数据以 byte[] 做交互；双方需要约定编解码；
可以使用零拷贝 `sendfile` 系统调用实现快速数据消费；

Producer 面向 Broker ，虽然期望面向的是 Topic ；

## Producer

`key` 默认使用 hash 分区器；

### `ProducerConfig` 生产者配置项

## Consumer

自动提交默认是 `5s` 异步提交（会出现**丢数据**和**重复消费**）

Partition 与 Consumer 一对多时， Consumer 每次 Poll 拉取多个分区的数据；

> 常识： 如果向多线程处理分区；
> 
> 每次 Poll ，用一个语义：一个 Job 启动；
> 一次 Job 用多线程并行处理分区，且一次 Job 应该被控制是串行的；

### `ConsumerConfig` 消费者者配置项
