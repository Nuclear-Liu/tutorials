package org.hui.basic;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.basic.event.LongEvent;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;

public class LongEventProcessor {
    private Disruptor<LongEvent> disruptor;

    public LongEventProcessor(int bufferSize, EventHandler<LongEvent> eventHandler) {
        this.disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
    }

    public LongEventProcessor(
            int bufferSize,
            ThreadFactory threadFactory,
            ProducerType producerType,
            WaitStrategy waitStrategy,
            EventHandler<LongEvent> eventHandler) {
        this.disruptor = new Disruptor<>(
                LongEvent::new,
                bufferSize,
                threadFactory, producerType,
                waitStrategy);
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
    }

    public void produce(EventTranslatorVararg<LongEvent> eventTranslatorVararg) {
        disruptor.getRingBuffer().publishEvent(eventTranslatorVararg);
    }

    public void produce(EventTranslatorOneArg<LongEvent, ByteBuffer> translator, ByteBuffer buffer) {
        disruptor.getRingBuffer().publishEvent(translator, buffer);
    }
}
