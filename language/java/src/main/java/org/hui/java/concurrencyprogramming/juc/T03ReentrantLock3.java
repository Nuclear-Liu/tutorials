package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class T03ReentrantLock3 {
    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                SleepHelper.sleepSeconds(1);
                log.info("{}", i);
            }
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        boolean locked = false;
        try{
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            log.info("m2. Locked: {}", locked);
        } catch (InterruptedException e) {
            log.info("interrupted.", e);
        } finally {
            if (locked)
                lock.unlock();
        }
    }

    public static void main(String[] args) {
        T03ReentrantLock3 rl = new T03ReentrantLock3();
        new Thread(rl::m1).start();
        SleepHelper.sleepSeconds(1);
        new Thread(rl::m2).start();
    }
}
