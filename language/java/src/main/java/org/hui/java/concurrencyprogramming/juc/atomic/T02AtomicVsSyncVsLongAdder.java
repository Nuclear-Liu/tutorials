package org.hui.java.concurrencyprogramming.juc.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class T02AtomicVsSyncVsLongAdder {
    public static final Logger LOGGER = LoggerFactory.getLogger(T02AtomicVsSyncVsLongAdder.class);
    static AtomicLong count1 = new AtomicLong(0L);
    static long count2 = 0L;
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10_000];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100_000; k++)
                    count1.incrementAndGet();
            });
        }

        long start = System.currentTimeMillis();

        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            t.join();

        long end = System.currentTimeMillis();

        LOGGER.info("Atomic: {} time {}", count1.get(), (end - start));
        // -----
        Object lock = new Object();

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100_000; k++) {
                    synchronized (lock) {
                        count2++;
                    }
                }
            });
        }

        start = System.currentTimeMillis();

        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            t.join();

        end = System.currentTimeMillis();

        LOGGER.info("Sync: {} time {}", count2, (end - start));
        // ----
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 1000_000; k++)
                    count3.increment();
            });
        }

        start = System.currentTimeMillis();

        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            t.join();

        end = System.currentTimeMillis();

        LOGGER.info("LongAdder: {} time {}", count3, (end - start));
    }
}
