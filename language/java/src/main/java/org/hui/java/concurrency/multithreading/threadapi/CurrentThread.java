package org.hui.java.concurrency.multithreading.threadapi;

public class CurrentThread {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // always true
                System.out.println(Thread.currentThread() == this);
            }
        };
        thread.start();

        String name = Thread.currentThread().getName();
        System.out.println("main".equals(name));
    }
}
