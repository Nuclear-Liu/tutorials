package org.hui.java.concurrencyprogramming.c001threadend;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T01Stop {
    private static final Logger LOGGER = LoggerFactory.getLogger(T01Stop.class);

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                LOGGER.info("go on.");
                SleepHelper.sleepSeconds(1);
            }
        });

        t.start();

        SleepHelper.sleepSeconds(5);

        t.stop();
    }
}
