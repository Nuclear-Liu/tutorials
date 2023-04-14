package org.hui.java.concurrencyprogramming.example.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Example05 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example05.class);

    public static String doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("------ doSomethingA ------");
        return "A Task Done";
    }

    public static String doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("------ doSomethingB ------");
        return "B Task Done";
    }

    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        Future<String> taskA = POOL_EXECUTOR.submit(() -> doSomethingA());
        // Future<String> taskB = POOL_EXECUTOR.submit(() -> doSomethingB());
        LOGGER.info(taskA.get());
    }
}
