package org.hui.java.concurrency.multithreading.threadapi.join;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        // 定义两个线程，并保存在 threads 中
        List<Thread> threads = IntStream.range(1, 3).mapToObj(ThreadJoin::create).collect(toList());
        // 启动这两个线程
        threads.forEach(Thread::start);
        TimeUnit.SECONDS.sleep(20);
        // 执行这两个线程的 join 方法
        for (Thread thread : threads) {
            thread.join();
        }
        // main 线程循环输出
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "#" + i);
            shortSleep();
        }
    }
    // 构造一个简单的线程，每个线程只是简单的循环输出
    private static Thread create(int seq) {
        return new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "#" + i);
                shortSleep();
            }
        }, String.valueOf(seq));
    }
    private static void shortSleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
