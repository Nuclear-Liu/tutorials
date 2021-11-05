package org.hui.java.concurrency.multithreading.threadapi;

import java.util.concurrent.TimeUnit;

public class ThreadInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Oh, i am be interrupted.");
            }
        });
        thread.start();

        // short block and make sure thread is started.
        TimeUnit.MILLISECONDS.sleep(2);
        thread.interrupt();
    }
}
