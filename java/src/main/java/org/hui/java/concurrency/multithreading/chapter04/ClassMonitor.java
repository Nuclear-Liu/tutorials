package org.hui.java.concurrency.multithreading.chapter04;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

public class ClassMonitor {
    public static synchronized void method1() {
        System.out.println(currentThread().getName() + " enter to method1");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static synchronized void method2() {
        System.out.println(currentThread().getName() + " enter to method2");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(ClassMonitor::method1, "T1").start();
        new Thread(ClassMonitor::method2, "T1").start();
    }
}
