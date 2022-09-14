package org.hui.disruptor.longevent.lambdas;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.disruptor.longevent.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * lambda example.
 */
public class LongEventMain {
    public static final Logger LOGGER = LoggerFactory.getLogger(LongEventMain.class);

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        // 默认多生产者模式
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event, sequence, endOfBatch) -> LOGGER.info("Event:{}", event));

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (long l = 0; true; l++) {
            byteBuffer.putLong(0, l);

            ringBuffer.publishEvent((event, sequence) -> event.set(byteBuffer.getLong(0)));
            // ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), byteBuffer);

            // Thread.sleep(1000);
        }
    }
}
