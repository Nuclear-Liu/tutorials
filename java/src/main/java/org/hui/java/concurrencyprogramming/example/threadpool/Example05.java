package org.hui.java.concurrencyprogramming.example.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class Example05 {
    public static String doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("------ doSomethingA ------");
        return "A Task Done";
    }

    public static String doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("------ doSomethingB ------");
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
        long start= System.currentTimeMillis();

        Future<String> taskA = POOL_EXECUTOR.submit(() -> doSomethingA());
        // Future<String> taskB = POOL_EXECUTOR.submit(() -> doSomethingB());
        log.info(taskA.get());
    }
}
