package org.hui.java.concurrencyprogramming.juc.c2101interview;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.LinkedList;

@Slf4j
public class MyContainer1<T> {
    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        while (list.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.info("MyContainer put option interrupted.", e);
            }
        }
        list.add(t);
        count++;
        this.notifyAll();
    }

    public synchronized T get() {
        T t = null;
        while (list.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                log.info("MyContainer get option interrupt.", e);
            }
        }
        t = list.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> c = new MyContainer1<>();

        // start consumer threads
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++)
                    log.info("consumer get: {}", c.get());
            }, "c" + i).start();
        }

        SleepHelper.sleepSeconds(2);

        // start product threads
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++)
                    c.put(Thread.currentThread().getName() + " " + j);
            }, "p" + i).start();
        }
    }

}
