package org.hui.concurrency;

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread("custom-thread") {
            @Override
            public void run() {
                System.out.println("thread name:" + Thread.currentThread().getName());
            }
        };
        t1.start();
    }
}