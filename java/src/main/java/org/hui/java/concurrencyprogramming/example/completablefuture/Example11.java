package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.Supplier;

@Slf4j
public class Example11 {
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void supplyAsync() throws ExecutionException, InterruptedException {
        // 1. create async task, return future
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("over");
                // return task result
                return "hello world";
            }
        });
        log.info("{}", future.get());
    }

    public static void supplyAsync(Executor executor) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("over");
            return "hello world";
        }, executor);
        log.info("{}", future.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        supplyAsync();
        supplyAsync(POOL_EXECUTOR);
    }
}
