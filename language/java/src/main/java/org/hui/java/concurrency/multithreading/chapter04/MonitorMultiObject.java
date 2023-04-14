package org.hui.java.concurrency.multithreading.chapter04;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class MonitorMultiObject {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        for (int i = 0; i < 5; i++) {
            new Thread(new Task()).start();
            /*new Thread(Task::run).start();*/ // syntax error
            /*Runnable aNew = Task::new;
            new Thread(aNew::run).start();*/ // not running
//            System.out.println("Create new Thread");
        }
    }
}
class Task implements Runnable {

    private final static Object MUTEX = new Object();

    public Task() {
        System.out.println("Task Constructor");
    }

    @Override
    public void run() {
        // ...
        synchronized (MUTEX) {
            // ...
            for (int i = 0; i < 3; i++) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " wake " + i);
            }
        }
        // ...
    }
}
