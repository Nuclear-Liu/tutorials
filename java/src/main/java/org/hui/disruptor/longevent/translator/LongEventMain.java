package org.hui.disruptor.longevent.translator;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.disruptor.longevent.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class LongEventMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(LongEventMain.class);

    public static void main(String[] args) {
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event, sequence, endOfBatch) -> LOGGER.info("use translator, event: {}", event));

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer buffer = ByteBuffer.allocate(8);

        for (long l = 0; true; l++) {
            buffer.putLong(0, l);

            producer.onData(buffer);
        }
    }
}
