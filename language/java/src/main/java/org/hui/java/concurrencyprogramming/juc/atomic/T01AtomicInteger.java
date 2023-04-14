package org.hui.java.concurrencyprogramming.juc.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class T01AtomicInteger {
    public static final Logger LOGGER = LoggerFactory.getLogger(T01AtomicInteger.class);
    private AtomicInteger count = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 10_000; i++) {
            count.incrementAndGet(); // count++
        }
    }

    public static void main(String[] args) {
        T01AtomicInteger t = new T01AtomicInteger();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                LOGGER.info("{} join exception.", o.getName());
            }
        });

        LOGGER.info("count: {}", t.count);
    }

}
