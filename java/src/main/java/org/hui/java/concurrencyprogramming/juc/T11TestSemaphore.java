package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class T11TestSemaphore {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(2);

        new Thread(() -> {
            try {
                s.acquire();
                log.info("T1 running.");
                Thread.sleep(200);
                log.info("T1 end.");
            } catch (InterruptedException e) {
                log.info("t1 interrupted.", e);
            } finally {
                s.release();
            }
        }).start();

        new Thread(() -> {
            try {
                s.acquire();
                log.info("T2 running.");
                Thread.sleep(200);
                log.info("T2 end.");
            } catch (InterruptedException e) {
                log.info("t2 interrupted.", e);
            } finally {
                s.release();
            }
        }).start();
    }
}
