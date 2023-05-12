package org.hui.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.event.LongEvent;
import org.hui.event_handler.LongEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.LongBuffer;

public class LongDisruptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongDisruptor.class);

    Disruptor<LongEvent> disruptor;

    LongBuffer longBuffer = LongBuffer.allocate(1);

    public LongDisruptor(int bufferSize, LongEventHandler eventHandler) {
        disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
    }

    public void product(long value) {
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        longBuffer.put(0,value);
        ringBuffer.publishEvent(((event, sequence, buffer) -> {
            event.setValue(longBuffer.get(0));
            LOGGER.info("product sequence:{} value:{}", sequence, event.getValue());
        }));
    }
}