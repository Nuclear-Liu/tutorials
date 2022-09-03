package org.hui.java.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;

/**
 * Producer.
 */
@Slf4j
public class LongEventProducer {
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
