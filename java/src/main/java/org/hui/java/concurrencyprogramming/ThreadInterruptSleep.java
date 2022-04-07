package org.hui.java.concurrencyprogramming;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadInterruptSleep {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                log.info("Thread is interrupted.");
                log.info("current interrupted: {}", Thread.currentThread().isInterrupted());
            }
        });

        t.start();

        SleepHelper.sleepSeconds(5);
        t.interrupt();
    }
}
