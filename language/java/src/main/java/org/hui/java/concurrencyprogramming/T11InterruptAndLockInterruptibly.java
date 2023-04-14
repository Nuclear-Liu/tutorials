package org.hui.java.concurrencyprogramming;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class T11InterruptAndLockInterruptibly {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            SleepHelper.sleepSeconds(10);
            lock.unlock();
            log.info("t1 end.");
        });

        t1.start();

        SleepHelper.sleepSeconds(1);

        Thread t2 = new Thread(() -> {
            log.info("t2 start.");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info("t2 thread is interrupt.", e);
//                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            log.info("t2 end.");
        });

        t2.start();

        SleepHelper.sleepSeconds(1);

        t2.interrupt();
    }

}
