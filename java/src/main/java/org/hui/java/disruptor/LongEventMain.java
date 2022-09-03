package org.hui.java.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {
        // 1. 创建 Ring Buffer 中事件元素的工厂对象
        LongEventFactory factory = new LongEventFactory();
        // 2. 指定 Ring Buffer 的大小，必须为 2 的幂次方
        int bufferSize = 1024;
        // 3. 构造 Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(
                factory,
                bufferSize,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE, new BlockingWaitStrategy());
        // 4. 注册消费者
        disruptor.handleEventsWith(new JournalConsumer(), new ReplicationConsumer()).then(new ApplicationConsumer());
        // 5. 启动 Disruptor 启动线程运行
        disruptor.start();
        // 6. 从 Disruptor 中获取 Ring Buffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        // 7. 创建生产者
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        // 8. 生产元素，并放入 Ring Buffer
        for (long l = 0; l < 100L; l++) {
            producer.onData(l);
            Thread.sleep(1000);
        }
        // 9. 挂起当前线程
        Thread.currentThread().join();
    }
}
