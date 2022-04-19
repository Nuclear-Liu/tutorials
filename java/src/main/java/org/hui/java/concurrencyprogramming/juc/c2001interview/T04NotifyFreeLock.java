package org.hui.java.concurrencyprogramming.juc.c2001interview;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class T04NotifyFreeLock {
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
            log.info("t2 start.");
            synchronized (lock) {
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        log.info("t2 interrupted.", e);
                    }
                }
                log.info("t2 end.");
                lock.notify();
            }
        }, "t2").start();

        SleepHelper.sleepSeconds(1);

        new Thread(() -> {
            log.info("t1 start.");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    log.info("add {}", i);
                    if (c.size() == 5) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            log.info("t1 interrupted.", e);
                        }
                    }
                    SleepHelper.sleepSeconds(1);
                }
            }
        }, "t1").start();
    }
}
