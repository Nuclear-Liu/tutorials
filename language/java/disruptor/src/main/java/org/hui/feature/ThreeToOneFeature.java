package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.TimeUnit;

public class ThreeToOneFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        Disruptor<StubEvent> disruptor = new Disruptor<>(StubEvent.EVENT_FACTORY, 1024, DaemonThreadFactory.INSTANCE);

        TransformingHandler handler1 = new TransformingHandler(0);
        TransformingHandler handler2 = new TransformingHandler(1);
        TransformingHandler handler3 = new TransformingHandler(2);
        CollatingHandler collator = new CollatingHandler();

        disruptor.handleEventsWith(handler1, handler2, handler3).then(collator);
        disruptor.start();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            disruptor.getRingBuffer().publishEvent((event, sequence, arg0) -> event.setValue(arg0), i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

final class CollatingHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consume:{}", event);
    }
}

final class TransformingHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int outputIndex;

    public TransformingHandler(final int outputIndex) {
        this.outputIndex = outputIndex;
    }

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        // do stuff.
        event.setTestString(event.getValue() + ":" + outputIndex);
    }
}
