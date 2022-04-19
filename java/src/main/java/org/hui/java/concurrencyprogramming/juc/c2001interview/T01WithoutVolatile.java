package org.hui.java.concurrencyprogramming.juc.c2001interview;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class T01WithoutVolatile {
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
                log.info("add {}.",i);
                SleepHelper.sleepSeconds(1);
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (c.size() == 5) {
                    break;
                }
            }
            log.info("t2 end.");
        }, "t2").start();
    }
}
