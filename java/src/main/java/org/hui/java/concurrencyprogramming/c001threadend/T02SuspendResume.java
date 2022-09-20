package org.hui.java.concurrencyprogramming.c001threadend;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T02SuspendResume {
    private static final Logger LOGGER = LoggerFactory.getLogger(T02SuspendResume.class);

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                LOGGER.info("go on.");
                SleepHelper.sleepSeconds(1);
            }
        });

        t.start();

        SleepHelper.sleepSeconds(5);

        t.suspend();
        LOGGER.info("t thread: {}", t.getState());
        SleepHelper.sleepSeconds(3);
        t.resume();
        LOGGER.info("t thread: {}", t.getState());
    }
}
