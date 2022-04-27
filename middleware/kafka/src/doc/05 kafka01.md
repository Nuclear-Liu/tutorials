# Kafka

## 单一实例

Broker 是用户空间的一个 JVM 进程； 
Kafka 数据主要依托于磁盘：
Producer 生产消息 Kafka 的 Broker 进程接收；
Broker 通过 Kernel 提供的 PageCache 页面缓存写入；
PageCache 具体写入可以通过配置进行调整；
写磁盘的粒度越小（每消息写磁盘），性能越慢，丢数据的可能性越大；

> **通过把单机持久化的可靠性转向集群提高保证**；

Data 数据文件；

> 消息具有顺序性，写入顺序写入，可以充分利用磁盘的顺序写入优势；

Data Index 数据索引文件（基于 offset 偏移）；记录数据的索引信息，降低遍历开销；

> 时间戳： Producer 生产时间（默认）、 Broker 接收时间；

Timestamp Index 基于索引的数据索引文件（基于时间戳的索引）；

Consumer 消费请求，Kafka 获取 offset 位置；
通过索引或遍历获取 `offset` 文件位置；
调用系统的 Kernel 的 `sendfile(in, offset, out)` 发送到网卡；

> Kafka 只是对数据进行发送，不会修改数据；

### Producer

客户端 `acks` 配置:

* `-1` 分布式环境：备机同步完成(最严苛)

    假设所有的 Follower 都在 ISR 内；消息要被所有的 Follower 取走；
    如果过程中有的 Follower 没有取走，则没有同步的 Follower 归为 OSR ；

* `0` 单机环境：消息发出即完成消息发送的语义；（更高的性能）

* `1` 单机环境：消息持久化到磁盘才完成消息发送的语义；



## 集群

Kafka 没有读写分离，只有主备；

Broker 分为 Leader 和 Follower ；
Producer 只能向 Leader 推送数据；

保证 Leader 与 Follower 之间的一致性：
* 强一致性：

    所有的 Follower 都保证一直后，回复 Producer 消息发送的语义；
    **要求所有节点必须存活**，一致性破坏了可用性；
* 最终一致性：

    过半的节点数据同步完成，回复 Producer 消息发送的语义；

* ISR(in-sync replicas) 取决于连通性、活跃性；（Kafka）

    比最终一致性更加灵活；弹性更高；

    Producer 客户端 `acks` 配置:

    * `-1` 分布式环境：备机同步完成(最严苛)

        假设所有的 Follower 都在 ISR 内；消息要被所有的 Follower 取走；
        如果过程中有的 Follower 没有取走，则没有同步的 Follower 归为 OSR ；会正常返回消息发送成功的语义；
        具体可以有多少个能够同步后为 ISR 通过配置管理；
        
        多个 Broker 的消息进度是一致的； Consumer 消费的就是 Producer 生产的消息；

    * `1` 默认值：消息持久化到磁盘才完成消息发送的语义；

        保证了 Leader 正确接收数据，不保证 Follower 消息状态；

        Producer 生产消息不受 Leader 中是否与 Follower 消息同步的影响；
        Consumer 默认只能消费到 ISR 中消息 `HW` 位置的消息

    * `0` 单机环境：消息发出即完成消息发送的语义；（更高的性能）


> ISR OSR AR
> 
> * ISR(in-sync replicas) 取决于连通性、活跃性；
> 
> * OSR(outof-sync replicas) 超过阈值时间，没有心跳
> 
> * AR 面向分区的副本集合； `AR = ISR + OSR`
> 
>     创建 topic 的时候给出了分区副本数， Controller 在创建的时候就已分配了 Broker 和分区对应关系，并得到该分区的 Broker 集合；

> LEO(LogEndOffset)
> 
> Producer 生产消息的 Leader 中最后一个消息的位置；

> HW(High Watermark) 高水位
> 
> ISR （所有可用节点）中最后一条同步完成的消息位置；

> * 不要强调磁盘的可靠性，转向异地多机的同步；
> * 如果拿磁盘持久化，在做 tradeoff 有效 PageCache 或者绝对磁盘，提供了配置；
> * 多级集群分布式的情况：强一致性、最终一致性（过半、ISR）；
> * 集群中有实例不可用，是否需要立刻尝试重启，同样需要权衡；
> 
> Redis 准求集群可用，而不是 AOF 准确性；
> 
> Kafka 追求 `ack:-1` ，而不是追求磁盘的可靠性；

Kafka 可以做存储层，不是全量存储；可以动态配置多久的数据忽略；

* 队列数据反复的查阅使用、推送，数据产生趋于稳定，可以用 Redis 或 Kafka (支持时间戳)；
* 裁剪历史数据 LW ；

> LW(Low Watermark) 低水位
> 
> Kafka 提供的能够访问的历史记录的临界位置；

元数据：Topic 信息；

## Broker
