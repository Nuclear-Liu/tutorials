package org.hui.disruptor.objectevent;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class ObjectEventMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectEventMain.class);

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        Disruptor<ObjectEvent> disruptor = new Disruptor<>(ObjectEvent::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWith((event, sequence, endOfBatch) -> LOGGER.info("Object Event: {}", event));

        RingBuffer<ObjectEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer buffer = ByteBuffer.allocate(50);

        for (long l = 0; true; l++) {
            ringBuffer.publishEvent((event, sequence, arg0, arg1) -> {event.setValue2(arg0);event.setValue2(arg1);}, l + "0", l + "1");
            Thread.sleep(3000);
        }
    }
}
