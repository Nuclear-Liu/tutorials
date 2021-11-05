package org.hui.java.concurrency.multithreading.threadapi;

import java.util.concurrent.TimeUnit;

public class RunningInterrupted {
    public static void main(String[] args) {
        // 判断当前线程是否被中断
        System.out.println("Main thread is interrupted? " + Thread.interrupted());
        // 中断当前线程
        Thread.currentThread().interrupt();
        System.out.println("Main thread is interrupted? " + Thread.currentThread().isInterrupted());
        try {
            System.out.println("I will sleep.");
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("I will be interrupted still.");
        }
    }
}
