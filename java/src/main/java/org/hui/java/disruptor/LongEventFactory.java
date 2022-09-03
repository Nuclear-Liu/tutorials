package org.hui.java.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Event Object Factory.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
