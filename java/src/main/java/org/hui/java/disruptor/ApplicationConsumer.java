package org.hui.java.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Consumer.
 * 真正处理业务逻辑的消费者.
 */
@Slf4j
public class ApplicationConsumer implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("Application Event:{}", event.getValue());
    }
}
