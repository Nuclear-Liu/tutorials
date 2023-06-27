package org.hui.basic;

import com.lmax.disruptor.EventTranslatorVararg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.basic.event.LongEvent;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

class LongEventProcessorWithLambdaTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void test() throws InterruptedException {
        int bufferSize = 1024*1024;
        LongEventProcessor processor = new LongEventProcessor(bufferSize, ((event, sequence, endOfBatch) -> {
            LOGGER.info("consume: {}", event);
        }));

        ByteBuffer buffer = ByteBuffer.allocate(8);
        EventTranslatorVararg<LongEvent> translatorVararg = (event, sequence, args) -> {
            event.setValue(buffer.getLong(0));
            LOGGER.info("produce: {}", event);
        };
        for (int i = 0; i<Integer.MAX_VALUE; i++) {
            buffer.putLong(0, i);
            processor.produce(translatorVararg);
//            TimeUnit.SECONDS.sleep(1);
        }

    }
}