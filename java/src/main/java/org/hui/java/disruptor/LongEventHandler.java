package org.hui.java.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {

    private static long count = 0;

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        count++;
        log.info("event: {} sqeuence: {} endOfBatch: {}", event.toString(), sequence, endOfBatch);
    }
}
