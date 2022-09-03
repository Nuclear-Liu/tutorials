package org.hui.java.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * consumer.
 * 将输入数据写入持久性日志的消费者.
 */
@Slf4j
public class JournalConsumer implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("Persist Event:{}", event.getValue());
    }
}
