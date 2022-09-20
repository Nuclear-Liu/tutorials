package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Example12 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example12.class);
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void thenRun() throws ExecutionException, InterruptedException {
        // 1. create async task, return future.
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task exec over");
            return "hello world";
        });
        // 2. get new future by oneFuture
        CompletableFuture<Void> twoFuture = oneFuture.thenRun(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("after oneFuture over doSomething");
        });
        LOGGER.info("{}", twoFuture.get());
    }

    public static void thenRunAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task exec over 01");
            return "hello world";
        });
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task exec over 02");
            return "hello world";
        });
        CompletableFuture<Void> nextFuture = future.thenRunAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("after oneFuture over doSomething 02");
        });
        CompletableFuture<Void> twoFuture = oneFuture.thenRunAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("after oneFuture over doSomething 01");
        });
        nextFuture.get();
        LOGGER.info("{}", twoFuture.get());
    }

    public static void main(String[] ages) throws ExecutionException, InterruptedException {
        thenRun();
        thenRunAsync();
    }
}
