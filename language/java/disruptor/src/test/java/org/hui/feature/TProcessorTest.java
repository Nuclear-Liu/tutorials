package org.hui.feature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TProcessorTest {
    private static final Logger LOGGER = LogManager.getLogger();
    @Test
    void test() throws InterruptedException {
        TProcessor<String> processor = new TProcessor<>(1024);
        for (int i = 0; i<Integer.MAX_VALUE; i++) {

            processor.produce(((event, sequence, arg0) -> {
                event.setValue(arg0);
                LOGGER.info("produce:{}",event);
            }),String.valueOf(i));
            TimeUnit.NANOSECONDS.sleep(100);
//            TimeUnit.SECONDS.sleep(1);
        }
    }

}