package org.hui.java.concurrency.multithreading.chapter05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefect defect = new SynchronizedDefect();
        Thread t1 = new Thread(defect::syncMethod, "T1");
        // make sure the t1 started
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        Thread t2 = new Thread(defect::syncMethod, "T2");
        t2.start();
        // make sure the t2 started
        TimeUnit.SECONDS.sleep(2);
        t2.interrupt();
        System.out.println(t2.isInterrupted());
        System.out.println(t2.getState());
    }
}
