package org.hui.java.concurrencyprogramming.c001threadend;

import org.hui.java.concurrencyprogramming.SleepHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T04InterruptAndNormalThread {
    private static final Logger LOGGER = LoggerFactory.getLogger(T04InterruptAndNormalThread.class);

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (!Thread.interrupted()) {
                // sleep wait
            }
            LOGGER.info("t thread end.");
        });

        t.start();

        SleepHelper.sleepSeconds(1);

        t.interrupt();
    }
}
