package org.hui.java.concurrencyprogramming.c001volatile;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T01HelloVolatile {
    private static volatile boolean running = true;
    public static void main(String[] args) {
        new Thread(T01HelloVolatile::m, "t1").start();

        SleepHelper.sleepSeconds(1);

        running = false;
    }

    private static void m() {
        log.info("m start.");
        while (running) {
            log.info("hello");
        }
        log.info("m end.");
    }

}
