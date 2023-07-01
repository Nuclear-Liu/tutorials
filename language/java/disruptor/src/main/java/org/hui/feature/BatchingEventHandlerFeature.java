package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BatchingEventHandlerFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new BatchingEventHandler());
        disruptor.start();

        for (int i = 0; i < 120; i++) {
            disruptor.publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(arg1);
            }, i, i + "mess");
        }

        TimeUnit.SECONDS.sleep(2);
        LOGGER.info("new batch");

        for (int i = 0; i < 326; i++) {
            disruptor.publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(arg1);
            }, i, i + "mess");
        }
        TimeUnit.SECONDS.sleep(1);
    }
}

class BatchingEventHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MAX_BATCH_SIZE = 100;
    private final List<StubEvent> batch = new ArrayList<>();

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        batch.add(event);
        if (endOfBatch || batch.size() >= MAX_BATCH_SIZE) {
            LOGGER.info("consumer state:{}",endOfBatch);
            processBatch(batch);
        }
    }

    private void processBatch(final List<StubEvent> batch) {
        LOGGER.info("consumer size:{}", batch.size());
        batch.clear();
    }
}
