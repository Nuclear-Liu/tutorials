package org.hui.java.concurrencyprogramming.juc.c2001interview;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class T01WithoutVolatile {
    public static final Logger LOGGER = LoggerFactory.getLogger(T01WithoutVolatile.class);
    List list = new LinkedList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T01WithoutVolatile c = new T01WithoutVolatile();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                LOGGER.info("add {}.", i);
                SleepHelper.sleepSeconds(1);
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (c.size() == 5) {
                    break;
                }
            }
            LOGGER.info("t2 end.");
        }, "t2").start();
    }
}
