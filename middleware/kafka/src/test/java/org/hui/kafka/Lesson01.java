package org.hui.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Lesson01 {

    /**
     * ./kafka-topics.sh --create --topic msb-items  --partitions 2  --replication-factor 2 --bootstrap-server 127.0.0.1:9092
     * ./kafka-topics.sh --create --topic msb-items  --partitions 2  --bootstrap-server kafka-server1:9092,kafka-server2:9092
     */
    @Test
    public void producer() throws ExecutionException, InterruptedException {
        String topic = "msb-items";
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-server1:9092,kafka-server2:9093");
        // kafka 持久化数据MQ 数据以 byte[] 做交互；双方需要约定编解码；可以使用零拷贝 sendfile 系统调用实现快速数据消费；
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 现在的 producer 仅为一个提供者，面向的是 Broker 虽然在使用的时候期望关联 Topic ；

        while (true) {
            /*
            三种商品，每种商品有线性的 3 个ID，相同的商品最好进入同一个分区里；
             */
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    ProducerRecord<String, String> record = new ProducerRecord<>(topic, "item" + j, "value" + i);
                    Future<RecordMetadata> send = producer.send(record);
                    RecordMetadata rm = send.get();
                    int partition = rm.partition();
                    long offset = rm.offset();
                    log.info("key: {} value: {} partition: {}, offset: {}", record.key(), record.value(), partition, offset);
                }
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void consumer() throws InterruptedException {
        Properties p = new Properties();
        // 基础配置
        p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-server1:9092,kafka-server2:9093");
        p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // 消费细节
        p.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "OOXX");
        // kafka not only MQ but also STORAGE
        p.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 提交
        p.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 一个运行的 Consumer 自己会维护自己的消费进度
        // 一旦自动提交，但还是异步：
        // 1. 没有到时间，挂了，没有正确提交 offset 重启后会造成重复消费；
        // 2. 一个批次的数据还没有写业务处理完成，但是批次的 offset 已经异步更新，挂了，重启后会丢失记录；
        // p.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"");
        // p.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, ""); // 通过 Poll 拉取数据，设置拉取最大值
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(p);

        // 订阅 kafka consumer 会动态负载均衡
        consumer.subscribe(Arrays.asList("msb-items"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                // 让出的分区
                Iterator<TopicPartition> iterator = partitions.iterator();
                while (iterator.hasNext()){
                    log.info("onPartitionsRevoked, Partition: {}", iterator.next().partition());
                }
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                // 获得分区
                Iterator<TopicPartition> iterator = partitions.iterator();
                while (iterator.hasNext()){
                    log.info("onPartitionsRevoked, Partition: {}", iterator.next().partition());
                }
            }
        });

        while (true) {
            // 拉取(可以设置超时时间） 获取多条记录（批次）
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(0));

            if (!records.isEmpty()) {
                log.info("Poll records: {}", records.count());

                // 优化

                Set<TopicPartition> partitions = records.partitions();
                for (TopicPartition partition : partitions) {
                    List<ConsumerRecord<String, String>> batchRecords = records.records(partition);
                    // 每个 Partition 按分区获取 Poll 回来的数据
                    // 可以线性或并行处理
                    Iterator<ConsumerRecord<String, String>> iterator = batchRecords.iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<String, String> record = iterator.next();
                        int par = record.partition();
                        long offset = record.offset();
                        String key = record.key();
                        String value = record.value();
                        log.info("partition: {} offset: {} key: {} value: {}", par, offset, key, value);
                    }
                }

//                Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
//                while (iterator.hasNext()) {
//                    // Partition 与 Consumer 是一对多关系
//                    ConsumerRecord<String, String> record = iterator.next();
//                    int partition = record.partition();
//                    long offset = record.offset();
//                    String key = record.key();
//                    String value = record.value();
//                    log.info("partition: {} offset: {} key: {} value: {}", partition, offset, key, value);
//                }
            }
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
