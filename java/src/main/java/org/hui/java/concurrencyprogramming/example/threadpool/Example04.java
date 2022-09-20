package org.hui.java.concurrencyprogramming.example.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Example04 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example04.class);

    public static void doSomethingA() throws InterruptedException {
        Thread.sleep(2000);
        LOGGER.info("------ doSomethingA ------");
    }

    public static void doSomethingB() throws InterruptedException {
        Thread.sleep(2000);
        LOGGER.info("------ doSomethingB ------");
    }

    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        // 1. exec doSomethingA
        POOL_EXECUTOR.execute(() -> {
            try {
                doSomethingA();
            } catch (InterruptedException e) {
                LOGGER.error("exec interrupted.", e);
            }
        });

        // 2. exec doSomethingB
        POOL_EXECUTOR.execute(() -> {
            try {
                doSomethingB();
            } catch (InterruptedException e) {
                LOGGER.error("exec interrupted.", e);
            }
        });

        LOGGER.info("exec time: {}", System.currentTimeMillis() - start);

        Thread.currentThread().join();
    }
}
