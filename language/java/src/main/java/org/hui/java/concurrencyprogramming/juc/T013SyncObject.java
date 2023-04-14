package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

@Slf4j
public class T013SyncObject {
    private static Object o = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (o) {
                log.info("t1 get lock object o.");
                SleepHelper.sleepSeconds(4);
            }
            log.info("t1 release lock object o.");
        });

        Thread t2 = new Thread(() -> {
            synchronized (o) {
                synchronized (o) {
                    log.info("t2 get lock object o.");
                    SleepHelper.sleepSeconds(4);
                }
                log.info("t2 release lock object o.");
            }
        });

        t1.start();
        o = new Object();

        t2.start();
    }
}
