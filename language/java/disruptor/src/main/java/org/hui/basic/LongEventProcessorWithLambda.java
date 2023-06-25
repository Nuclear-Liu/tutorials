package org.hui.basic;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.basic.event.LongEvent;

public class LongEventProcessorWithLambda {
    private Disruptor<LongEvent> disruptor;

    public LongEventProcessorWithLambda(int bufferSize, EventHandler<LongEvent> eventHandler ) {
        this.disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
    }
    public void produce(EventTranslatorVararg<LongEvent> eventTranslatorVararg) {
        disruptor.getRingBuffer().publishEvent(eventTranslatorVararg);
    }
}
