package org.hui.basic.consumer;

import com.lmax.disruptor.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.basic.event.LongEvent;

public class LongEventHandler implements EventHandler<LongEvent> {
    private static final Logger LOGGER = LogManager.getLogger(LongEventHandler.class);
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("Event: " + event);
    }
}
