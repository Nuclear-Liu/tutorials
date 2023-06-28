package org.hui.feature;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.*;

public class DynamicallyAddHandlerFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool(DaemonThreadFactory.INSTANCE);

        // Build a disruptor and start it.
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        RingBuffer<StubEvent> ringBuffer = disruptor.start();

        // Construct 2 batch event processors.
        DynamicHandler handler1 = new DynamicHandler();
        BatchEventProcessor<StubEvent> processor1 = new BatchEventProcessor<>(
                ringBuffer,
                ringBuffer.newBarrier(),
                handler1);

        DynamicHandler handler2 = new DynamicHandler();
        BatchEventProcessor<StubEvent> processor2 = new BatchEventProcessor<>(
                ringBuffer,
                ringBuffer.newBarrier(),
                handler2);

        // Dynamically add both sequences to the ring buffer
        ringBuffer.addGatingSequences(processor1.getSequence(), processor2.getSequence());

        // Start the new batch processors.
        executor.execute(processor1);
        executor.execute(processor2);

        // Async produce
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                ringBuffer.publishEvent(StubEvent.TRANSLATOR, i, i + "message");
                try {
                    TimeUnit.NANOSECONDS.sleep(20_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        TimeUnit.SECONDS.sleep(4);

        // Remove a processor.
        // Stop the processor
        processor2.halt();
        handler2.awaitShutdown();
        // Remove the gating sequence from the ring buffer
        ringBuffer.removeGatingSequence(processor2.getSequence());

        while (true) ;
    }
}

class DynamicHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CountDownLatch shutdownLatch = new CountDownLatch(1);

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consumer:{}", event);
    }

    @Override
    public void onStart() {
        LOGGER.info("consumer start");
    }

    @Override
    public void onShutdown() {
        LOGGER.info("consumer stop");
        shutdownLatch.countDown();
        LOGGER.info("release shutdown latch");
    }

    public void awaitShutdown() throws InterruptedException {
        LOGGER.info("await shutdown latch");
        shutdownLatch.await();
        LOGGER.info("acquired shutdown latch");
    }
}
