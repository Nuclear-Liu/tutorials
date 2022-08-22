package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ExampleWhenComplete {
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

            log.info("one task is over");
            return "hello world";
        });
        CompletableFuture<String> future = oneFuture.whenComplete((str, e) -> {
            if (null == e) {
                log.info("{}", str);
            } else {
                log.error("one task exec expression:", e);

            }
        });
        log.info("{}", future.get());
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        whenComplete();
    }
}
