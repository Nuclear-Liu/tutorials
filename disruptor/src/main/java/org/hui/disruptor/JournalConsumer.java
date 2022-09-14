package org.hui.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * consumer.
 * 将输入数据写入持久性日志的消费者.
 */
public class JournalConsumer implements EventHandler<LongEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalConsumer.class);

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("Persist Event:{}", event.getValue());
    }
}
