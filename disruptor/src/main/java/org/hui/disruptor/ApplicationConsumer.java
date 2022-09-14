package org.hui.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Consumer.
 * 真正处理业务逻辑的消费者.
 */
public class ApplicationConsumer implements EventHandler<LongEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConsumer.class);

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("Application Event:{}", event.getValue());
    }
}
