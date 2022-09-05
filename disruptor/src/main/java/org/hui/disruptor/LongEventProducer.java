package org.hui.disruptor;

import com.lmax.disruptor.RingBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer.
 */
public class LongEventProducer {

    private static final Logger log = LoggerFactory.getLogger(LongEventProducer.class);

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long bb) {
        long sequence = ringBuffer.next(); // 第一阶段获取序列号
        try {
            LongEvent event = ringBuffer.get(sequence); // 获取序列号对应实体元素
            event.set(bb); // 修改元素的值
        } finally {
            ringBuffer.publish(sequence); // 发布元素
        }
    }
}
