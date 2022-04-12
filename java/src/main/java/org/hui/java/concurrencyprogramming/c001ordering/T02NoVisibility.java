package org.hui.java.concurrencyprogramming.c001ordering;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class T02NoVisibility {
    private static volatile boolean ready = false;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            log.info(String.valueOf(number));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new ReaderThread();
        t.start();
        number = 42;
        ready = true;
        t.join();
    }
}
