package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class Example09 {
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 1. create CompletableFuture
        CompletableFuture<String> future = new CompletableFuture<>();

        // 2. start async
        POOL_EXECUTOR.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 设置计算结果到 future
            log.info("------ {} set future task ------", Thread.currentThread().getName() );
            future.complete("hello word");
        });

        // 3. wait result
        log.info("------ main thread wait future result ------");
        log.info(future.get());
        log.info("------ main thread got future result ------");
    }
}
