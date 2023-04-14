package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class T04ReentrantLock4 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                lock.lockInterruptibly();
                log.info("t1 start.");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                log.info("t1 end.");
            } catch (InterruptedException e) {
                log.info("interrupted.");
            } finally {
                log.info("t1 unlock.");
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
//                lock.lockInterruptibly();
                lock.lock();
                log.info("t2 start.");
                TimeUnit.SECONDS.sleep(5);
                log.info("t2 end.");
            } catch (InterruptedException e) {
                log.info("interrupted.");
            } finally {
                lock.unlock();
            }
        });
        t2.start();
        SleepHelper.sleepSeconds(1);
        t1.interrupt();
    }
}
