package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.TimeUnit;

public class NamedEventHandlerFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new NamedEventHandler("consumer_1"))
                .then(new NamedEventHandler("consumer_2"));
        disruptor.start();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            disruptor.getRingBuffer().publishEvent((event, sequence, arg0, arg1) -> {
                event.setValue(arg0);
                event.setTestString(arg1);
            },i, "message");
            TimeUnit.MICROSECONDS.sleep(180);
        }
    }
}
class NamedEventHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String name;

    NamedEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        LOGGER.info("consume:{}",event);
    }

    @Override
    public void onStart() {
        final Thread currentThread = Thread.currentThread();
        LOGGER.info("old thread name:{}",currentThread.getName());
        currentThread.setName(name);
    }
}
