package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.TimeUnit;

public class PipelinerFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(
                        new ParallelHandler(0, 3),
                        new ParallelHandler(1, 3),
                        new ParallelHandler(2, 3))
                .then(new JoiningHandler());
        RingBuffer<StubEvent> ringBuffer = disruptor.start();

        for (int i = 0; i < 100; i++) {
            ringBuffer.publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(null);
            }, i, null);
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
class ParallelHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int ordial;
    private final int totalHandlers;
    ParallelHandler(int ordial, int totalHandlers) {
        this.ordial = ordial;
        this.totalHandlers = totalHandlers;
    }
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (sequence % totalHandlers == ordial) {
            event.setTestString(ordial + "-" + event.getValue());
        }
    }
}
class JoiningHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (event.getTestString() == null) {
            LOGGER.error("joining handler:{}", event);
        } else {
            LOGGER.info("consumer:{},comsumer:{}", this.getClass().getSimpleName(), event);
        }
    }
}
