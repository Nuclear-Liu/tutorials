package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T03SleepYieldJoin {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
               log.info("{} is running", Thread.currentThread().getName());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                log.info("t2 join before");
                t1.join();
                log.info("t2 join after.");
            } catch (InterruptedException e) {
                log.info("join exception.", e);
            }
        });
        t1.start();
        t2.start();
    }
}
