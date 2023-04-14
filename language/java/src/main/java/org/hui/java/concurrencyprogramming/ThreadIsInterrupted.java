package org.hui.java.concurrencyprogramming;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程中断.
 */
@Slf4j
public class ThreadIsInterrupted {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (; ; ) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("Thread is interrupted.");
                    log.info("{}, interrupted: {}", Thread.currentThread().getName(), Thread.currentThread().isInterrupted());
                    break;
                }
            }
        });
        t.start();
        SleepHelper.sleepSeconds(2);
        t.interrupt();
    }
}
