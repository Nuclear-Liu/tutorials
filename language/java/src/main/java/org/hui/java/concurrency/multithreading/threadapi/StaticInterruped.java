package org.hui.java.concurrency.multithreading.threadapi;

import java.util.concurrent.TimeUnit;

public class StaticInterruped {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println(Thread.interrupted());
            }
        });
        thread.setDaemon(true);
        thread.start();

        // shortly block make sure the thread is started.
        TimeUnit.MILLISECONDS.sleep(2);
        thread.interrupt();
    }
}
