package org.hui.basic;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.hui.basic.consumer.LongEventHandler;
import org.hui.basic.event.LongEvent;
import org.hui.basic.event.LongEventFactory;

public class LongEventProcessor {
    private Disruptor<LongEvent> disruptor;

    public LongEventProcessor(int bufferSize,LongEventHandler eventHandler) {
        disruptor = new Disruptor<>(new LongEventFactory(), bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
    }
}
