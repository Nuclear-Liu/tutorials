package org.hui.java.concurrencyprogramming.juc.c2001interview;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class T04NotifyFreeLock {
    public static final Logger LOGGER = LoggerFactory.getLogger(T04NotifyFreeLock.class);
    volatile List list = new LinkedList<>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T04NotifyFreeLock c = new T04NotifyFreeLock();

        final Object lock = new Object();

        new Thread(() -> {
            LOGGER.info("t2 start.");
            synchronized (lock) {
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        LOGGER.info("t2 interrupted.", e);
                    }
                }
                LOGGER.info("t2 end.");
                lock.notify();
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
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            LOGGER.info("t1 interrupted.", e);
                        }
                    }
                    SleepHelper.sleepSeconds(1);
                }
            }
        }, "t1").start();
    }
}
