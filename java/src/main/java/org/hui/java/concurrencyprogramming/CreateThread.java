package org.hui.java.concurrencyprogramming;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Create Thread。
 */
@Slf4j
public class CreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            log.info("Run Way 1 . extends Thread.");
        }
    }

    static class MyRun implements Runnable {

        @Override
        public void run() {
            log.info("Run Way 2 . implements Runnable.");
        }
    }

    static class MyCall implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("Run Way 5 . implements Callable.");
            return "success";
        }
    }

    // 线程启动的 5 种方式
    // 本质上只有一种 new Thread().
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(() -> log.info("Run Way 3 . Lambda.")).start();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> log.info("Rum Way 4 . ThreadPool."));

        Future<String> future = service.submit(new MyCall());
        String result = future.get();
        log.info("Run Way 5 . Result: {}", result);
        service.shutdown();

        FutureTask<String> task = new FutureTask<>(new MyCall());
        Thread thread = new Thread(task);
        thread.start();
        log.info("Run Way 5 . With Thread: {}", task.get());
    }
}
