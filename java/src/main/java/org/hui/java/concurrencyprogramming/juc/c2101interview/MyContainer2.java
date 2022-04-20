package org.hui.java.concurrencyprogramming.juc.c2101interview;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class MyContainer2<T> {

    private final LinkedList<T> list = new LinkedList<>();
    private final int MAX = 10;
    private int count = 0;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t) {
        lock.lock();
        try {
            while (list.size() == MAX) {
                producer.await();
            }
            list.add(t);
            count++;
            consumer.signalAll();
        } catch (InterruptedException e) {
            log.info("MyContainer put option interrupted.", e);
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T t = null;
        lock.lock();
        try {
            while (list.size() == 0) {
                consumer.await();
            }
            t = list.removeFirst();
            count--;
            producer.signalAll();
        } catch (InterruptedException e) {
            log.info("MyContainer get option interrupted.", e);
        } finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {
        MyContainer2 c = new MyContainer2();

        // start consumer threads
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++)
                    log.info("consumer get: {}", c.get());
            }, "c" + i).start();
        }

        SleepHelper.sleepSeconds(2);

        // start producer threads
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++)
                    c.put(Thread.currentThread().getName() + " " + j);
            }, "p" + i).start();
        }
    }
}
