package org.hui.disruptor;

import org.hui.event_handler.LongEventHandler;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class LongDisruptorTest {

    @Test
    void test() throws ExecutionException, InterruptedException {
        LongDisruptor longDisruptor = new LongDisruptor(1024, new LongEventHandler());

        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 10; i++) {
                longDisruptor.product(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        completableFuture.get();
    }

}