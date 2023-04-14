package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleThenAccept {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleThenAccept.class);
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void thenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task over");
            return "hello world";
        });
        CompletableFuture<Void> twoFuture = oneFuture.thenAccept((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("after oneFuture over doSomething {}", str);
        });
        LOGGER.info("{}", twoFuture.get());
    }

    public static void thenAcceptAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task over");
            return "hello world";
        }, POOL_EXECUTOR);
        CompletableFuture<Void> twoFuture = oneFuture.thenAcceptAsync((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LOGGER.info("after oneFuture over doSomething {}", str);
        }, POOL_EXECUTOR);
        LOGGER.info("{}", twoFuture.get());
    }

    public static void main(String[] agrs) throws ExecutionException, InterruptedException {
        thenAccept();
        thenAcceptAsync();
    }
}
