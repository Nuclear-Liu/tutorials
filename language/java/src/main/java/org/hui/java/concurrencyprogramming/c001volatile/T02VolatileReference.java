package org.hui.java.concurrencyprogramming.c001volatile;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T02VolatileReference {

    private static final Logger LOGGER = LoggerFactory.getLogger(T02VolatileReference.class);

    private static volatile A a = new A();

    public static void main(String[] args) {
        new Thread(a::m, "t1").start();
        SleepHelper.sleepSeconds(1);
        a.running = false;
    }

    private static class A {
        boolean running = true;

        void m() {
            LOGGER.info("m start.");
            while (running) {

            }
            LOGGER.info("m end.");
        }
    }
}
