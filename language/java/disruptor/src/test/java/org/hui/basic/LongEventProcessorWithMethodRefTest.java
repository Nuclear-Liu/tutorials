package org.hui.basic;

import com.lmax.disruptor.EventTranslatorVararg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.basic.event.LongEvent;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

class LongEventProcessorWithMethodRefTest {
    private static final Logger LOGGER = LogManager.getLogger();

    static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        LOGGER.info("consumer:{}",event);
    }
    static void translate(LongEvent event, long sequence, ByteBuffer buffer) {
        event.setValue(buffer.getLong(0));
        LOGGER.info("produce: {}", event);
    }
    @Test
    void test() throws InterruptedException {
        int bufferSize = 1024*1024;
        LongEventProcessor processor = new LongEventProcessor(bufferSize, LongEventProcessorWithMethodRefTest::handleEvent);

        ByteBuffer buffer = ByteBuffer.allocate(8);

        for (int i = 0; i<Integer.MAX_VALUE; i++) {
            buffer.putLong(0, i);
            processor.produce(LongEventProcessorWithMethodRefTest::translate,buffer);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
