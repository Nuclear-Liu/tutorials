package org.hui.java.concurrencyprogramming.c001volatile;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T02VolatileReference {

    private static volatile A a = new A();

    public static void main(String[] args) {
        new Thread(a::m, "t1").start();
        SleepHelper.sleepSeconds(1);
        a.running = false;
    }

    private static class A {
        boolean running = true;
        void m() {
            log.info("m start.");
            while (running) {

            }
            log.info("m end.");
        }
    }
}
