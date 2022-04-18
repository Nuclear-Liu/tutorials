package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class T02ReentrantLock2 {
    Lock lock = new ReentrantLock();

    void m1() {
        try {
            lock.lock();
            for (int i = 0; i < 18; i++) {
                SleepHelper.sleepSeconds(1);
                log.info("{}", i);
            }
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        try {
            lock.lock();
            log.info("m2...");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        T02ReentrantLock2 rl = new T02ReentrantLock2();
        new Thread(rl::m1).start();
        SleepHelper.sleepSeconds(1);
        new Thread(rl::m2).start();
    }
}
