package org.hui.java.concurrencyprogramming.example.futuretask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class Example08 {
    public static String doSomethingA() throws InterruptedException {
        Thread.sleep(2000);
        log.info("------ doSomethingA ------");
        return "TaskAResult";
    }

    public static String doSomethingB() throws InterruptedException {
        Thread.sleep(2000);
        log.info("------ doSomethingB ------");
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
        log.info("{} {}", taskAResult, taskBResult);
        log.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
