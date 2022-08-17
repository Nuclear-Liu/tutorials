package org.hui.java.concurrencyprogramming.example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example02 {
    public static void doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("sleep exception.", e);
        }
        log.info("------ doSomethingA ------");
    }

    public static void doSomethingB() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("sleep exception.", e);
        }
        log.info("------ doSomethingB ------");
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
        log.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
