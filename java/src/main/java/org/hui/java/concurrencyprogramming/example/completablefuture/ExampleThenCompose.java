package org.hui.java.concurrencyprogramming.example.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Slf4j
public class ExampleThenCompose {
    // 1. async task return future.
    public static CompletableFuture<String> doSomethingOne(String encodedCompanyId) {
        // 1.1 create sync task.
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("one task: {}", encodedCompanyId);
            return encodedCompanyId;
        });
    }
    // 2. open async task return future.
    public static CompletableFuture<String> doSomethingTwo(String companyId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = companyId + ":java";
            log.info("two task: {}", result);
            return result;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Function<String, CompletionStage<String>> stringCompletionStageFunction = id -> doSomethingTwo(id);
        CompletableFuture<String> result = doSomethingOne("123").thenCompose(stringCompletionStageFunction);

        log.info("{}", result.get());
    }
}
