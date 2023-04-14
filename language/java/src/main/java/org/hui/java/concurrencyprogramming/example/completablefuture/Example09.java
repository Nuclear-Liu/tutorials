package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Example09 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example09.class);
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
            LOGGER.info("------ {} set future task ------", Thread.currentThread().getName());
            future.complete("hello word");
        });

        // 3. wait result
        LOGGER.info("------ main thread wait future result ------");
        LOGGER.info(future.get());
        LOGGER.info("------ main thread got future result ------");
    }
}
