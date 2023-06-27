package org.hui.object;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class ObjectEventProcessorTest {
    @Test
    void test() throws InterruptedException {
        ObjectEventProcessor processor = new ObjectEventProcessor(1024);

        for (int i = 0; i<Integer.MAX_VALUE; i++) {

            processor.produce(((event, sequence, arg0) -> {
                event.setValue(arg0);
            }),String.valueOf(i));
            TimeUnit.NANOSECONDS.sleep(100);
//            TimeUnit.SECONDS.sleep(1);
        }
    }
}
