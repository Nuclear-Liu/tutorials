package org.hui.java.concurrencyprogramming;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static org.hui.java.concurrencyprogramming.SleepHelper.sleepSeconds;

/**
 * Thread status switch.
 */
@Slf4j
public class ThreadStatus {
    public static void main(String[] args) throws InterruptedException {
        // =================================
        Thread t1 = new Thread(() -> {
            log.info("2: " + Thread.currentThread().getState());
            for (int i = 0; i < 3; i++) {
                sleepSeconds(1);
                log.info("{} ", i);
            }

        });
        log.info("1: {}", t1.getState());
        t1.start();
        t1.join(); // 等待线程结束
        log.info("3: {}", t1.getState());

        // =================================
        Thread t2 = new Thread(() -> {
            LockSupport.park(); // 等待被叫醒
            log.info("t2 gon on.");
            sleepSeconds(5);
        });
        t2.start();
        sleepSeconds(1);
        log.info("4: {}", t2.getState());

        LockSupport.unpark(t2); // 唤醒线程
        sleepSeconds(1);
        log.info("5: {}", t2.getState());

        // =================================
        final Object o = new Object();
        Thread t3 = new Thread(() -> {
            synchronized (o) {
                log.info("t3 得到了锁 o");
            }
        });
        new Thread(() -> {
            synchronized (o) {
                sleepSeconds(5);
            }
        }).start();
        sleepSeconds(1);
        t3.start();
        sleepSeconds(1);
        log.info("6: {}", t3.getState());

        // =================================
        final Lock lock = new ReentrantLock();
        Thread t4 = new Thread(() -> {
            lock.lock(); // JUC 的锁 CAS 锁
            log.info("t4 得到锁.");
            lock.unlock();
        });

        new Thread(() -> {
            lock.lock();
            sleepSeconds(5);
            lock.unlock();
        }).start();
        sleepSeconds(1);
        t4.start();
        sleepSeconds(1);
        log.info("7: {}", t4.getState());

        // =================================
        Thread t5 = new Thread(() -> LockSupport.park());

        t5.start();
        sleepSeconds(1);
        log.info("8: {}", t5.getState());
        LockSupport.unpark(t5);
    }
}
