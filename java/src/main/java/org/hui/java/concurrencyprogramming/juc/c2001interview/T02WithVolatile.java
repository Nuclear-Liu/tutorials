package org.hui.java.concurrencyprogramming.juc.c2001interview;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class T02WithVolatile {
    // 添加 volatile 修饰 线程间可见
    volatile List list = new LinkedList();
//    volatile  List list = Collections.synchronizedList(new LinkedList<>());

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        T02WithVolatile c = new T02WithVolatile();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                log.info("add {}.", i);
//                SleepHelper.sleepSeconds(1);
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
