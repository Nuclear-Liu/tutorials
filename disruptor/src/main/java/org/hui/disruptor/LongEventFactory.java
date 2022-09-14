package org.hui.disruptor;

import com.lmax.disruptor.EventFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event Object Factory.
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongEventFactory.class);

    @Override
    public LongEvent newInstance() {
        LongEvent longEvent = new LongEvent();
        LOGGER.info("create event's hash : {}", longEvent.hashCode());
        return longEvent;
    }
}
