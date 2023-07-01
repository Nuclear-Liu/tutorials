package org.hui.feature;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.feature.event.StubEvent;

import java.util.concurrent.TimeUnit;

public class HandlerExceptionOnTranslateFeature {
    private static final Logger LOGGER = LogManager.getLogger();
    static final int NO_VALUE_SPECIFIED = -1;
    public static void main(String[] args) throws InterruptedException {
        Disruptor<StubEvent> disruptor = new Disruptor<>(
                StubEvent.EVENT_FACTORY,
                1024,
                DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new StubHandler());
        disruptor.start();

        EventTranslator<StubEvent> translator = ((event, sequence) -> {
            event.setValue(NO_VALUE_SPECIFIED);
            if (sequence % 3==0){
                throw new RuntimeException("skipping");
            }
            event.setValue((int)sequence);
        });

        for (int i = 0; i < 10; i++) {
            try {
                disruptor.publishEvent(translator);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        TimeUnit.SECONDS.sleep(5);
    }
}
class StubHandler implements EventHandler<StubEvent> {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void onEvent(StubEvent event, long sequence, boolean endOfBatch) throws Exception {
        if (event.getValue() == HandlerExceptionOnTranslateFeature.NO_VALUE_SPECIFIED) {
            LOGGER.info("discarded");
        } else {
            LOGGER.info("processed:{}",event);
        }
    }
}
