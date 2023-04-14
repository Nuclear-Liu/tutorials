package org.hui.java.concurrencyprogramming.juc.c2001interview;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class T03NotifyHoldingLock {
    public static final Logger LOGGER = LoggerFactory.getLogger(T03NotifyHoldingLock.class);
    volatile List list = new LinkedList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T03NotifyHoldingLock c = new T03NotifyHoldingLock();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                LOGGER.info("t2 start.");
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        LOGGER.info("t2 interruped.", e);
                    }
                }
            }
        }, "t2").start();

        SleepHelper.sleepSeconds(1);

        new Thread(() -> {
            LOGGER.info("t1 start.");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    LOGGER.info("add {}", i);

                    if (c.size() == 5) {
                        lock.notify();
                    }

                    SleepHelper.sleepSeconds(1);
                }
            }
        }, "t2").start();
    }

}
