package org.hui.basic;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorVararg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.basic.event.LongEvent;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

class LongEventProcessorWithTranslatorTest {
    private static final Logger LOGGER = LogManager.getLogger();

    static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        LOGGER.info("consumer:{}",event);
    }
    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> translator = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
        private static final Logger LOGGER = LogManager.getLogger();
        @Override
        public void translateTo(LongEvent event, long sequence, ByteBuffer arg0) {
            event.setValue(arg0.getLong(0));
            LOGGER.info("produce: {}", event);
        }
    };
    @Test
    void test() throws InterruptedException {
        int bufferSize = 1024*1024;
        LongEventProcessor processor = new LongEventProcessor(bufferSize, LongEventProcessorWithMethodRefTest::handleEvent);

        ByteBuffer buffer = ByteBuffer.allocate(8);

        for (int i = 0; i<Integer.MAX_VALUE; i++) {
            buffer.putLong(0, i);
            processor.produce(translator,buffer);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
