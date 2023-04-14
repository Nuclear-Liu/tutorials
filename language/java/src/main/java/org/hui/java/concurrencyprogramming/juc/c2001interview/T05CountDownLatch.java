package org.hui.java.concurrencyprogramming.juc.c2001interview;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class T05CountDownLatch {

    volatile List list = new ArrayList<Object>();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T05CountDownLatch c = new T05CountDownLatch();

        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            log.info("t2 start.");
            if (c.size() != 5) {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.info("t2 interrupted.", e);
                }
            }
            log.info("t2 end.");
        }, "t2").start();

        SleepHelper.sleepSeconds(1);

        new Thread(() -> {
            log.info("t1 start");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                log.info("add {}", i);
                if (c.size() == 5) {
                    latch.countDown();
                }
            }
        }, "t1").start();
    }
}
