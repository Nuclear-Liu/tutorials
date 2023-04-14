package org.hui.java.concurrencyprogramming.example.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExampleAnyOf {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleAnyOf.class);

    public static CompletableFuture<String> doSomethingOne(String encodedCompanyId) {
        return CompletableFuture.supplyAsync(() -> {
            LOGGER.info("one task: {}", encodedCompanyId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return encodedCompanyId;
        });
    }

    public static CompletableFuture<String> doSomethingTwo(String companyId) {
        return CompletableFuture.supplyAsync(() -> {
            String result = companyId + ":java";
            LOGGER.info("two task: {}", result);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    public static void anyOf() throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> futures = new ArrayList<>();

        futures.add(doSomethingOne("1"));
        futures.add(doSomethingOne("2"));
        futures.add(doSomethingOne("3"));

        CompletableFuture<Object> result = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()]));

        LOGGER.info("result class: {}", result.getClass());
        LOGGER.info("result.get() class: {}", result.get().getClass());
        LOGGER.info("{}", result.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        anyOf();
    }
}
