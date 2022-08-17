package org.hui.java.concurrencyprogramming.example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example01 {
    public static void doSomethingA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("sleep exception.",e);
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

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 1. exec doSomethingA
        doSomethingA();
        // 2. exec doSomethingB
        doSomethingB();
        log.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
