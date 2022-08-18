package org.hui.java.concurrencyprogramming.example.futuretask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class Example07 {
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
        log.info("{} {}", taskAResult, taskBResult);
        log.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
