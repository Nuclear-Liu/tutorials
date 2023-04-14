package org.hui.java.concurrencyprogramming.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example02 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example02.class);
    public static void doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("sleep exception.", e);
        }
        LOGGER.info("------ doSomethingA ------");
    }

    public static void doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("sleep exception.", e);
        }
        LOGGER.info("------ doSomethingB ------");
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        // 1. 开启异步单元执行任务A
        Thread threadA = new Thread("thread-a") {
            @Override
            public void run() {
                doSomethingA();
            }
        };
//        Thread threadA = new Thread(() -> {
//            doSomethingA();
//        });
        Runnable taska = () -> doSomethingA();
        threadA.start();
        // 2. exec doSomethingB
        doSomethingB();

        threadA.join();
        LOGGER.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
