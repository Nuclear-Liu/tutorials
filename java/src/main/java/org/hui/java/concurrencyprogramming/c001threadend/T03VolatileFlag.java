package org.hui.java.concurrencyprogramming.c001threadend;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T03VolatileFlag {
    private static final Logger LOGGER = LoggerFactory.getLogger(T03VolatileFlag.class);

    private static volatile boolean running = true;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            long i = 0L;
            while (running) {
                // wait recv accept
                i++;
            }
            LOGGER.info("end and i = {}", i);
        });

        t.start();

        SleepHelper.sleepSeconds(1);

        running = false;
    }
}
