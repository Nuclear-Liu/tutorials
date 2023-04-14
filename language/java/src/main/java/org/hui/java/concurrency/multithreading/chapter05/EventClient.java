package org.hui.java.concurrency.multithreading.chapter05;

import java.util.concurrent.TimeUnit;

public class EventClient {
    public static void main(String[] args) {
        int threadSize = 10;
        final EventQueue eventQueue = new EventQueue();
        for (int i = 0; i < threadSize; i ++) {
            new Thread(() -> {
                for (; ; ) {
                    eventQueue.offer(new EventQueue.Event());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Producer:" + i).start();
        }
        for (int i = 0; i < threadSize; i ++) {
            new Thread(() -> {
                for (; ; ) {
                    eventQueue.take();
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Consumer:" + i).start();
        }
    }
}
