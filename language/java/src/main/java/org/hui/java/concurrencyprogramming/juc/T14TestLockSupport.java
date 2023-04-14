package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.concurrencyprogramming.SleepHelper;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class T14TestLockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                log.info("{}", i);
                if (i == 5)
                    LockSupport.park();
                SleepHelper.sleepSeconds(1);
            }
        });

        LockSupport.unpark(t);
        t.start();
    }
}
