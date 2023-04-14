package org.hui.java.concurrencyprogramming.example.futuretask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Example07 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example07.class);

    public static String doSomethingA() throws InterruptedException {
        Thread.sleep(2000);
        LOGGER.info("------ doSomethingA ------");
        return "TaskAResult";
    }

    public static String doSomethingB() throws InterruptedException {
        Thread.sleep(2000);
        LOGGER.info("------ doSomethingB ------");
        return "TaskBResult";
    }

    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        // 1. create future task A
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            String result = null;
            try {
                result = doSomethingA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        });

        // 2. exec task A // pool return can not anything.
        Future<?> future = POOL_EXECUTOR.submit(futureTask);
        Callable<String> task = () -> {
            return "Hello";
        };
        Future<String> submit = POOL_EXECUTOR.submit(task);
        System.out.println(submit.get());

        // 3. exec task B
        String taskBResult = doSomethingB();

        // 4. sync get A
        String taskAResult = futureTask.get();

        Object o = future.get();

        // 5. print result
        LOGGER.info("{} {}", taskAResult, taskBResult);
        LOGGER.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
