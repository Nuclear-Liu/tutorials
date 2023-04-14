package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExampleAllOf {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleAllOf.class);

    public static CompletableFuture<String> doSomethingOne(String encodedCompanyId) {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info("one task: {}", encodedCompanyId);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public static void allOf() throws ExecutionException, InterruptedException {
        List<CompletableFuture> futures = new ArrayList<>();

        futures.add(doSomethingOne("1"));
        futures.add(doSomethingOne("2"));
        futures.add(doSomethingOne("3"));
        futures.add(doSomethingOne("4"));

        CompletableFuture<Void> future = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[futures.size()]));

        LOGGER.info("{}", future.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        allOf();
    }
}
