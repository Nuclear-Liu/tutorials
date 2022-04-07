package org.hui.java.concurrencyprogramming;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadInterrupedWait {

    private static Object o = new Object();

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            synchronized (o) {
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    log.info("Thread is interrupted.");
                    log.info("Current Thrad interrupted: {}", Thread.currentThread().isInterrupted());
                }
            }
        });

        t.start();

        SleepHelper.sleepSeconds(5);

        t.interrupt();
    }
}
