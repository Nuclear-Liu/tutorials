package org.hui.java.concurrencyprogramming.c001syncbasics;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T01WhatIsLock {
    private static Object o = new Object();

    public static void main(String[] args) {
        Runnable r = () -> {
            synchronized (o) {
                log.info("{} start.", Thread.currentThread().getName());
                SleepHelper.sleepSeconds(2);
                log.info("{} end.", Thread.currentThread().getName());
            }
        };

        for (int i = 0; i < 3; i++) {
            new Thread(r).start();
        }

    }
}
