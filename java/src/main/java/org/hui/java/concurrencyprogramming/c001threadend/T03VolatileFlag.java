package org.hui.java.concurrencyprogramming.c001threadend;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T03VolatileFlag {

    private static volatile boolean running = true;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            long i = 0L;
            while (running) {
                // wait recv accept
                i++;
            }
            log.info("end and i = {}", i);
        });

        t.start();

        SleepHelper.sleepSeconds(1);

        running = false;
    }
}
