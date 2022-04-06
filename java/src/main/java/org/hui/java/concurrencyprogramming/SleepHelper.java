package org.hui.java.concurrencyprogramming;

import java.util.concurrent.TimeUnit;

/**
 * Tools.
 */
public class SleepHelper {
    public static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
