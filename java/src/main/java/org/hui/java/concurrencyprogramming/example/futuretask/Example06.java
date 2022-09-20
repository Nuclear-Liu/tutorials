package org.hui.java.concurrencyprogramming.example.futuretask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Example06 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example06.class);
    public static String doSomethingA() throws InterruptedException {
        Thread.sleep(2000);
//        throw new RuntimeException();
        LOGGER.info("------ doSomethingA ------");
        return "TaskAResult";
    }
    public static String doSomethingB() throws InterruptedException {
        Thread.sleep(2000);
        LOGGER.info("------ doSomethingB ------");
        return "TaskBResult";
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        // 1. create future task
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            String result = null;

            try {
                result = doSomethingA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        });

        // 2. 开启异步单元执行任务A
        Thread thread = new Thread(futureTask, "threadA");
        thread.start();

        // 3. exec task B
        String taskBResult = doSomethingB();

        // 4. 同步等待线程A运行结束
        String taskAResult = futureTask.get();

        // 5. 打印两个任务执行结果
        LOGGER.info("{} {}", taskAResult, taskBResult );
        LOGGER.info("exec time: {}", (System.currentTimeMillis() - start));
    }
}
