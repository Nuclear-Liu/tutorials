package org.hui.java.concurrencyprogramming.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example01 {
    public static final Logger LOGGER = LoggerFactory.getLogger(Example01.class);

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

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 1. exec doSomethingA
        doSomethingA();
        // 2. exec doSomethingB
        doSomethingB();
        LOGGER.info("exec time: {}", System.currentTimeMillis() - start);
    }
}
