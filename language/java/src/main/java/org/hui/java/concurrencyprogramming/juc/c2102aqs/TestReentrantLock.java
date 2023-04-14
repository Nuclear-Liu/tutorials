package org.hui.java.concurrencyprogramming.juc.c2102aqs;

import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            SleepHelper.sleepSeconds(200);
        }).start();
        SleepHelper.sleepSeconds(8);
        lock.lock();
        lock.unlock();
    }
}
