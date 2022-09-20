package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleThenApply {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleThenApply.class);
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void thenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task is over");
            return "hello world";
        }, POOL_EXECUTOR);
        CompletableFuture<String> twoFuture = oneFuture.thenApply((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("two task is over");
            return str + " java";
        });
        LOGGER.info("{}", twoFuture.get());
    }

    public static void thenApplyAsync() {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task is over");
            return "hello world";
        }, POOL_EXECUTOR);
        CompletableFuture<String> twoFuture = oneFuture.thenApplyAsync((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("two task is over");
            return str + " java";
        }, POOL_EXECUTOR);
        LOGGER.info("{}", twoFuture);
    }

    public static void main(String[] argc) throws ExecutionException, InterruptedException {
        thenApply();
        thenApplyAsync();
    }
}
