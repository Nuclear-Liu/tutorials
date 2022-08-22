package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ExampleThenAccept {
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
            log.info("one task over");
            return "hello world";
        });
        CompletableFuture<Void> twoFuture = oneFuture.thenAccept((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("after oneFuture over doSomething {}", str);
        });
        log.info("{}", twoFuture.get());
    }
    public static void thenAcceptAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("one task over");
            return "hello world";
        }, POOL_EXECUTOR);
        CompletableFuture<Void> twoFuture = oneFuture.thenAcceptAsync((str) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("after oneFuture over doSomething {}", str);
        }, POOL_EXECUTOR);
        log.info("{}", twoFuture.get());
    }

    public static void main(String[] agrs) throws ExecutionException, InterruptedException {
        thenAccept();
        thenAcceptAsync();
    }
}
