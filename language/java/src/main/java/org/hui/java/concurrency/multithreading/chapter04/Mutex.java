package org.hui.java.concurrency.multithreading.chapter04;

import java.util.concurrent.TimeUnit;

public class Mutex {
    private final static Object MUTEX = new Object();
//    private final static Object MUTEX = null; // java.lang.NullPointerException

    public void accessResource() {
        synchronized (MUTEX) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "is end.");
        }
    }

    public static void main(String[] args) {
        final Mutex mutex = new Mutex();
        for (int i = 0; i < 5; i++) {
            new Thread(mutex::accessResource).start();
        }
    }
}
