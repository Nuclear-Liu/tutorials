package org.hui.java.concurrencyprogramming.c001syncbasics;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T01WhatIsLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(T01WhatIsLock.class);
    private static Object o = new Object();

    public static void main(String[] args) {
        Runnable r = () -> {
            synchronized (o) {
                LOGGER.info("{} start.", Thread.currentThread().getName());
                SleepHelper.sleepSeconds(2);
                LOGGER.info("{} end.", Thread.currentThread().getName());
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(r).start();
        }

    }
}
