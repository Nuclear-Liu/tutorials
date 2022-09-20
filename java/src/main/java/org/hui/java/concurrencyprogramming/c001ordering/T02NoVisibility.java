package org.hui.java.concurrencyprogramming.c001ordering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T02NoVisibility {

    private static final Logger LOGGER = LoggerFactory.getLogger(T02NoVisibility.class);

    private static volatile boolean ready = false;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            LOGGER.info(String.valueOf(number));
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
