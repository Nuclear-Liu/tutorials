package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExampleCompleteExceptionally {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleCompleteExceptionally.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. create one CompletableFuture
        CompletableFuture<String> future = new CompletableFuture<>();

        // 2. open thread exec task
        new Thread(() -> {
            try {
                if (true) {
                    throw new RuntimeException("exception test");
                }
                future.complete("ok");
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
            LOGGER.info("------ {} ------", Thread.currentThread().getName());
        }, "thread-1").start();

        LOGGER.info("{}", future.exceptionally(t -> "default").get());
    }
}
