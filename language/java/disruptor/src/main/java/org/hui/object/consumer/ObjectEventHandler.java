package org.hui.object.consumer;

import com.lmax.disruptor.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.object.event.ObjectEvent;

public class ObjectEventHandler<T> implements EventHandler<ObjectEvent<T>> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consumer: {}", event);
    }
}
