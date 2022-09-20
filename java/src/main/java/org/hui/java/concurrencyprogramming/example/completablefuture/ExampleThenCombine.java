package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExampleThenCombine {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleThenCombine.class);

    public static CompletableFuture<String> doSomethingOne(String encodedCompanyId) {
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
        CompletableFuture<String> future = doSomethingOne("123")
                .thenCombine(doSomethingTwo("456"),
                        (one, two) -> one + two);
        LOGGER.info("{}", future.get());
    }
}
