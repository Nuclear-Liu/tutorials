package org.hui.java.concurrencyprogramming.example.futuretask;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class Example06 {
    public static String doSomethingA() throws InterruptedException {
        Thread.sleep(2000);
//        throw new RuntimeException();
        log.info("------ doSomethingA ------");
        return "TaskAResult";
    }
    public static String doSomethingB() throws InterruptedException {
        Thread.sleep(2000);
        log.info("------ doSomethingB ------");
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
        log.info("{} {}", taskAResult, taskBResult );
        log.info("exec time: {}", (System.currentTimeMillis() - start));
    }
}
