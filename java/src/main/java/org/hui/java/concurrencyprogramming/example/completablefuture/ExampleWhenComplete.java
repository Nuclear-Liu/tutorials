package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleWhenComplete {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleWhenComplete.class);
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void whenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
//                throw new RuntimeException();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LOGGER.info("one task is over");
            return "hello world";
        });
        CompletableFuture<String> future = oneFuture.whenComplete((str, e) -> {
            if (null == e) {
                LOGGER.info("{}", str);
            } else {
                LOGGER.error("one task exec expression:", e);

            }
        });
        LOGGER.info("{}", future.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        whenComplete();
    }
}
