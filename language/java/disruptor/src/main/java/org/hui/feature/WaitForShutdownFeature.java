package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WaitForShutdownFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws TimeoutException, InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(StubEvent.EVENT_FACTORY, 16, DaemonThreadFactory.INSTANCE);

        CountDownLatch shutdownLatch = new CountDownLatch(2);

        disruptor.handleEventsWith(new SDHandler(shutdownLatch)).then(new SDHandler(shutdownLatch));
        disruptor.start();

        CompletableFuture.runAsync(() -> {
            while (true) {
                long next = disruptor.getRingBuffer().next();
                disruptor.getRingBuffer().get(next).setValue((int) next);
                disruptor.getRingBuffer().publish(next);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        TimeUnit.SECONDS.sleep(2);

        disruptor.shutdown(10, TimeUnit.SECONDS);

        LOGGER.info("wait shutdown");
        shutdownLatch.await();
        LOGGER.info("shutdown");
    }
}
class SDHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CountDownLatch latch;
    public SDHandler(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onStart() {
        // on start
    }

    @Override
    public void onShutdown() {
        latch.countDown();
        LOGGER.info("shutdown handler on shutdown");
    }

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consume:{}",event);
    }
}
