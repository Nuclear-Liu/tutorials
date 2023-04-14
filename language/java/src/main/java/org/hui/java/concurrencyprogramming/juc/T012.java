package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T012 {
    private volatile boolean running = true;

    private void m() {
        log.info("m start.");
        while (running) {
            // do something.
        }
        log.info("n end.");
    }

    public static void main(String[] args) {
        T012 t = new T012();

        new Thread(t::m, "t1").start();

        SleepHelper.sleepSeconds(1);

        t.running = false;
    }
}
