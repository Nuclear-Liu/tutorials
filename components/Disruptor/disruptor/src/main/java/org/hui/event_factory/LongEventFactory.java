package org.hui.event_factory;

import com.lmax.disruptor.EventFactory;
import org.hui.event.LongEvent;

public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
