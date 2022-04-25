package org.hui.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

//        while (true) {
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
//        }
    }

    @Test
    public void consumer() {
        Properties p = new Properties();
        p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-server1:9092,kafka-server2:9093");
        p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
    }
}
