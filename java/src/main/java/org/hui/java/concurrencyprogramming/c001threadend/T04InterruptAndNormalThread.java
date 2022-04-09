package org.hui.java.concurrencyprogramming.c001threadend;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T04InterruptAndNormalThread {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (!Thread.interrupted()) {
                // sleep wait
            }
            log.info("t thread end.");
        });

        t.start();

        SleepHelper.sleepSeconds(1);

        t.interrupt();
    }
}
