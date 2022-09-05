package org.hui.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Consumer.
 * 将输入数据发送到另一台机器以确保保存在数据的远程副本的消费者。
 */
public class ReplicationConsumer implements EventHandler<LongEvent> {

    private static final Logger log = LoggerFactory.getLogger(ReplicationConsumer.class);

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("Replication Event:{}", event.getValue());
    }
}
