# Producer

> Kafka 是按照批次推送的吗？
> 
> 
> `log` 文件中是一条一条存的吗？
> 

## `KafkaProducer`

将记录发布到 Kafka 群集的 Kafka 客户端。

线程安全，可以多个线程共用一个实例，抛出一个 `Sender` 线程生产消息；

### `Partitioner` 分区器接口

默认分区器实现： `DefaultPartitioner` ：
* `keyBates == null` : 轮询存放到存活的分区中；
* `keyBates != null` : 相同 Key 保证进入同一个分区中；

### `RecordAccumulator`

记录累加器；可以批次提交；

配置： 
* `ProducerConfig.BUFFER_MEMORY_CONFIG` 配置累加器大小，默认 `32M` 。 **优化点**
* `ProducerConfig.MAX_BLOCK_MS_CONFIG` 空间用完最大阻塞时长，默认 `60S` 。

双端队列 `Deque<ProducerBatch>` ：
* 每个 topic 维护一个双端队列

#### `ProducerBatch`

配置： 
* `ProducerConfig.BATCH_SIZE_CONFIG` 配置 `ProducerBatch` 大小，默认`16K` 。
    1. msg size < `BATCH_SIZE_CONFIG` : 可以填充多个 （池化管理）
    2. msg size > `BATCH_SIZE_CONFIG` : 实际批次大小为 msg size （使用结束即回收，容易产生内存碎片和系统调用复杂度）**优化点**

### `Sender` 

实现了 `Runnable` 接口，创建 `KafkaProducer` 会启动一个新的线程

* `run()`
  * `runOnce()`
    * `sendProducerData()`


## `KafkaProducer.send()` 发送

配置：
* `ProducerConfig.LINGER_MS_CONFIG` 
    * `0` ： 不需要计时，接收到及发送
    * `>0` ： （非阻塞）时间窗口到达或者生产者批次满(`ProducerBatch`)时发送

1. 拦截器： `ProducerInterceptors.onSend()`
2. `doSend()`
   1. 序列化
   2. 获取 `partition` ，创建 `TopicPartition` 对象
   3. 向累加器 `RecordAccumulator` 累加 `accumulator.append()` (添加时锁定 topic 对应的双端队列)

> ACK 配置
> 
> * `0` 的情况下，消息发送到 `ProducerBatch` 后即返回；
> * `1` Leader 返回确认
> * `-1` 分区所有可用分区返回确认；
