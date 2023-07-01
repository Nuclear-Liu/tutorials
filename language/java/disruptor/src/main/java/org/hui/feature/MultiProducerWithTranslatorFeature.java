package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class MultiProducerWithTranslatorFeature {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.MULTI,
                new YieldingWaitStrategy());
        disruptor.handleEventsWith(new Consumer()).then(new Consumer()).then(new ClearConsumer());

        Publisher p = new Publisher();

        for (int i = 0; i < 1024; i++)
        {
            disruptor.getRingBuffer().publishEvent(p, i, i+Thread.currentThread().getName());
        }
        LOGGER.info("start disruptor");
        disruptor.start();
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                disruptor.getRingBuffer().publishEvent(p,i, Thread.currentThread().getName() + i);
                try {
                    TimeUnit.NANOSECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                disruptor.getRingBuffer().publishEvent(p,i, Thread.currentThread().getName() + i);
                try {
                    TimeUnit.NANOSECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        while (true);
    }
}
class Publisher implements EventTranslatorTwoArg<StubEvent,Integer,String> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void translateTo(StubEvent event, long sequence, Integer arg0, String arg1) {
        event.setValue(arg0);
        event.setTestString(arg1);
        LOGGER.info("publish:{}",event);
    }
}
class Consumer implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consume: {}", event);
    }
}
class ClearConsumer implements EventHandler<StubEvent> {
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.clear();
    }
}
