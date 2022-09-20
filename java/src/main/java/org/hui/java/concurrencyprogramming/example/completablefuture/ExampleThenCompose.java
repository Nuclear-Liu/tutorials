package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class ExampleThenCompose {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleThenCompose.class);

    // 1. async task return future.
    public static CompletableFuture<String> doSomethingOne(String encodedCompanyId) {
        // 1.1 create sync task.
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("one task: {}", encodedCompanyId);
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
            LOGGER.info("two task: {}", result);
            return result;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Function<String, CompletionStage<String>> stringCompletionStageFunction = id -> doSomethingTwo(id);
        CompletableFuture<String> result = doSomethingOne("123").thenCompose(stringCompletionStageFunction);

        LOGGER.info("{}", result.get());
    }
}
