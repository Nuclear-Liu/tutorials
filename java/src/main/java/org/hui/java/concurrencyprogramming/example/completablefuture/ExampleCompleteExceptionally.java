package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ExampleCompleteExceptionally {
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
            log.info("------ {} ------", Thread.currentThread().getName());
        }, "thread-1").start();

        log.info("{}", future.exceptionally(t -> "default").get());
    }
}
