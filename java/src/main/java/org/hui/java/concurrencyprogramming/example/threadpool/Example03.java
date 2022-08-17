package org.hui.java.concurrencyprogramming.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Example03 {
    public static void doSomethingA() throws InterruptedException {
        Thread.sleep(3000);
        log.info("------ doSomethingA ------");
    }

    public static void doSomethingB() throws InterruptedException {
        Thread.sleep(2000);
        log.info("------ doSomethingB ------");
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
                log.error("exec interrupted.", e);
            }
        });

        // 2. exec doSomethingB
        doSomethingB();

        log.info("exec time: {}", System.currentTimeMillis() - start);

        Thread.currentThread().join();
    }
}
