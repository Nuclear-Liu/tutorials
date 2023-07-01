package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WaitForProcessingFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws InterruptedException {
        final Disruptor<StubEvent> disruptor = new Disruptor<>(StubEvent.EVENT_FACTORY, 1024, DaemonThreadFactory.INSTANCE);

        ConsumerEvent firstConsumer = new ConsumerEvent();
        ConsumerEvent lastConsumer = new ConsumerEvent();
        disruptor.handleEventsWith(firstConsumer).then(lastConsumer);
        final RingBuffer<StubEvent> ringBuffer = disruptor.getRingBuffer();

        EventTranslator<StubEvent> translator = ((event, sequence) -> event.setValue((int)sequence-4));

        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                ringBuffer.tryPublishEvent(translator);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        TimeUnit.MILLISECONDS.sleep(100);
        disruptor.start();

        waitForSpecificConsumer(disruptor, lastConsumer, ringBuffer);
        waitForRingBufferToBeIdle(ringBuffer);
    }
    private static void waitForRingBufferToBeIdle(final RingBuffer<StubEvent> ringBuffer) {
        while (ringBuffer.getBufferSize() - ringBuffer.remainingCapacity() != 0) {
            // wait for priocessing...
        }
    }
    private static void waitForSpecificConsumer(
            final Disruptor<StubEvent> disruptor,
            final ConsumerEvent lastConsumer,
            final RingBuffer<StubEvent> ringBuffer) {
        long lastPublishedValue;
        long sequenceValueFor;
        do {
            lastPublishedValue = ringBuffer.getCursor();
            sequenceValueFor = disruptor.getSequenceValueFor(lastConsumer);
        } while (sequenceValueFor < lastPublishedValue);
    }
}
class ConsumerEvent implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("{}",event);
        TimeUnit.MILLISECONDS.sleep(101);
    }
}
