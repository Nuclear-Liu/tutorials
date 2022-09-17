package org.hui.disruptor.longevent.translator;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import org.hui.disruptor.longevent.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class LongEventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LongEventProducer.class);

    private final RingBuffer<LongEvent> ringBuffer;


    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        @Override
        public void translateTo(LongEvent event, long sequence, ByteBuffer buffer) {
            event.set(buffer.getLong(0));
        }
    };

    public void onData(ByteBuffer buffer) {
        ringBuffer.publishEvent(TRANSLATOR, buffer);
    }
}
