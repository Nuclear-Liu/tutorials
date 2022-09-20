package org.hui.java.concurrencyprogramming.c001volatile;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T01HelloVolatile {
    private static final Logger LOGGER = LoggerFactory.getLogger(T01HelloVolatile.class);
    private static volatile boolean running = true;

    public static void main(String[] args) {
        new Thread(T01HelloVolatile::m, "t1").start();

        SleepHelper.sleepSeconds(1);

        running = false;
    }

    private static void m() {
        LOGGER.info("m start.");
        while (running) {
            LOGGER.info("hello");
        }
        LOGGER.info("m end.");
    }

}
