package org.hui.java.concurrency.multithreading.threadapi;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupted2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    // ignore the exceprtion
                    // here the interrupt flag will be clear.
                    System.out.printf("I an be interrupted ? %s\n", Thread.currentThread().isInterrupted());
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("Thread is interrupted ? %s\n", thread.isInterrupted());
        thread.interrupt();
        TimeUnit.MILLISECONDS.sleep(2);
        System.out.printf("Thread is interrupted ? %s\n", thread.isInterrupted());
    }
}
