package org.hui.java.concurrencyprogramming.juc.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class T01AtomicInteger {
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
                log.info("{} join exception.", o.getName());
            }
        });

        log.info("count: {}", t.count);
    }

}
