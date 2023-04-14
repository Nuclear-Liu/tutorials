package org.hui.java.concurrency.multithreading.threadapi;

import java.util.concurrent.TimeUnit;

public class ThreadTimeUnit {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        TimeUnit.MINUTES.sleep(2);
        TimeUnit.SECONDS.sleep(33);
        TimeUnit.MINUTES.sleep(44);
        TimeUnit.MILLISECONDS.sleep(512);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
