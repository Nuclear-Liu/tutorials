package org.hui.java.concurrency.multithreading.chapter05;

import java.util.concurrent.TimeUnit;

public class SynchronizedDefect {
    public synchronized void syncMethod() {
        try {
            TimeUnit.SECONDS.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefect defect = new SynchronizedDefect();
        Thread t1 = new Thread(defect::syncMethod, "T1");
        // make sure the t1 started.
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        Thread t2 = new Thread(defect::syncMethod, "T2");
        t2.start();
    }
}
