package org.hui.java.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Consumer.
 * 将输入数据发送到另一台机器以确保保存在数据的远程副本的消费者。
 */
@Slf4j
public class ReplicationConsumer implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        log.info("Replication Event:{}", event.getValue());
    }
}
