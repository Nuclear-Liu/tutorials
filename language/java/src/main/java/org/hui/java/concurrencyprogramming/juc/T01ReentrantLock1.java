package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T01ReentrantLock1 {
    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            SleepHelper.sleepSeconds(1);
            log.info("{}", i);
            if (i == 2) m2();
        }
    }

    synchronized void m2() {
        log.info("m2...");
    }

    public static void main(String[] args) {
        T01ReentrantLock1 rl = new T01ReentrantLock1();
        new Thread(rl::m1).start();
        SleepHelper.sleepSeconds(1);
        new Thread(rl::m2).start();
    }
}
