package org.hui.event_handler;

import com.lmax.disruptor.EventHandler;
import org.hui.event.LongEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongEventHandler implements EventHandler<LongEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongEventHandler.class);

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consumer sequence:{} endOfBatch:{} LongEvent:{}", sequence, endOfBatch, event);
    }
}
