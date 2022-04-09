package org.hui.java.concurrencyprogramming.c001threadend;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T02SuspendResume {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                log.info("go on.");
                SleepHelper.sleepSeconds(1);
            }
        });

        t.start();

        SleepHelper.sleepSeconds(5);

        t.suspend();
        log.info("t thread: {}", t.getState());
        SleepHelper.sleepSeconds(3);
        t.resume();
        log.info("t thread: {}", t.getState());
    }
}
