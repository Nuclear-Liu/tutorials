package org.hui.java.concurrencyprogramming.example.futuretask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Example08 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example08.class);

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

        // 1. 开启异步单元执行任务 A
        Future<String> futureTask = POOL_EXECUTOR.submit(() -> {
            String result = null;
            try {
                result = doSomethingA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        });

        // 2. 执行任务 B
        String taskBResult = doSomethingB();

        // 3. 同步等待线程 A 运行结束
        String taskAResult = futureTask.get();

        // 4. print exec result.
        LOGGER.info("{} {}", taskAResult, taskBResult);
        LOGGER.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
