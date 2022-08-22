package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ExampleThenApply {
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
            log.info("one task is over");
            return "hello world";
        }, POOL_EXECUTOR);
        CompletableFuture<String> twoFuture = oneFuture.thenApply((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("two task is over");
            return str + " java";
        });
        log.info("{}", twoFuture.get());
    }

    public static void thenApplyAsync() {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("one task is over");
            return "hello world";
        }, POOL_EXECUTOR);
        CompletableFuture<String> twoFuture = oneFuture.thenApplyAsync((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("two task is over");
            return str + " java";
        }, POOL_EXECUTOR);
        log.info("{}", twoFuture);
    }

    public static void main(String[] argc) throws ExecutionException, InterruptedException {
        thenApply();
        thenApplyAsync();
    }
}
