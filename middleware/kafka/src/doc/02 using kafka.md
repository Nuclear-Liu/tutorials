## Topic

创建 `topic_name` ， `partition_num` 个分区， `replcation_num` 个备份；

`kafka-topic.sh --zookeeper node1:port,node2:prot/kafka --create --topic topic_name --partitions partition_num --replication-factor replication_num`

显式 Topic

`kafka-topic.sh --zookeeper node1:prot,node2:prot/kafka --list`

显式 `topic_name` 详细信息

`kafka-topic.sh --zookeeper node1:prot,node2:prot/kafka --describe --topic topic_name`

## Consumer

`group_id` 组的消费者消费 `topic_name` 的 Topic

`kafra-console-consumer.sh --bootstrap-server node1:port,node2:prot --topic topic_name --group group_id`

## Producer

创建对 `topic_name` 的生产者

`kafka-console-producer.sh --broker-list node1:port,node2:port --topic topic_name`

生产者生产的消息默认轮询写入每个 Partition ；

## Group

查看 `group_name` 组的信息

`kafka-consumer-groups.sh --bootstrap-server node1:prot,node2:port --describe --group group_name`
