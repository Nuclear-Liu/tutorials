package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class T04ThreadState {
    private static class MyThread extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("{}", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                log.info("{} interrupted.", Thread.currentThread().getName(), e);
            }
        }
    }
    public static void main(String[] args) {
        Thread t = new MyThread();

        log.info("Thread state: {}", t.getState());

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            log.info("join exception.", e);
        }
        log.info("Thread state: {}", t.getState());
    }
}
