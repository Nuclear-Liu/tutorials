# Partition

> 分区 vs Producer **同一个 Topic 下**
> 
> 如果没有顺序上的约束的情况下，数据可以直接散列到不同的分区，属于水平扩展；
> 
> 如果消息种类多，而且需要同一类消息需要有序，此时不再是水平扩展，消息转化为 <k,v> ， k 为消息类别，相同 `k` 一定去到一个分区；
> Broker 会保证 Producer 推送的消息的顺序；
> 一个分区会有不同的消息 `k` （类别），且不同的 `k` （类别）是交叉的即相同的 `k` 在一个分区没有按照 `k` （类别）分类；

> 同一个类别的消息如何保证有序性？
> 
> 可以是一个类别由一个 Producer 负责产生（进入同一个 Partition ）；
 
> 分区 vs Consumer **同一个 Topic 下**
> 
> Consumer 可以是拉取或者推送：
> Kafka 中采用了**拉取**的方式；
> 
> 拉取数据时：拉取数据的粒度：按照批次拉取或者按照单条记录粒度；
> 批次拉取充分利用顺序 IO 的速度优势；所以按照**批次消费**；


> 消息拉取 vs 消息推送
> 
> 推送：消息中间件主动推送， Consumer 可能会网卡阻塞，如果要避免网卡阻塞的情形，需要消息中简件维护消费状态信息；（反而增加了维护的整体复杂度）
> 
> 拉取： Consumer 主动按照需要请求数据，

> Consumer 处理消费的模型：单线程 vs 多线程？
> 
> * 单线程：每条记录以事务单位来处理； offset 按照记录来更新（无论对事务、offset频率更新高）；
> * 多线程：相同 `k` 类别作为单一线程处理；可能会出现重复消费或丢失；
>     
>     间接聊到大数据（Spark的处理方式）；响应式编程；
> 
> * 流式计算，能多线程的部分尽量多线程，批次的头或尾的绝对更新，依赖于事务的反馈，将整个批次的事务环节交由一个线程处理；

> Kafka 以什么粒度更新 offset （按照批次 or 按照单条记录）？
> 
> 从效率来讲，最好按照批次更新；
