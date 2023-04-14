package org.hui.java.concurrency.multithreading.threadapi.shutdown;

import java.util.concurrent.TimeUnit;

public class InterruptedThreadExit {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("I will start work");
            while (!Thread.currentThread().isInterrupted()) {
                // working.
            }
            System.out.println("I will be exiting.");
        });
        t.start();
        TimeUnit.SECONDS.sleep(6);
        System.out.println("System will be shutdown.");
        t.interrupt();
    }
}
