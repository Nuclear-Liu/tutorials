package org.hui.java.container.map;

import lombok.extern.slf4j.Slf4j;
import org.hui.java.container.Constants;

import java.util.Hashtable;
import java.util.UUID;

@Slf4j
public class T01TestHashtable {

    static Hashtable<UUID, UUID> m = new Hashtable<>();

    static int count = Constants.COUNT;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];
    static final int THREAD_COUNT = Constants.THREAD_COUNT;

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread {
        int start;
        int gap = count / THREAD_COUNT;

        public MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < start + gap; i++) {
                m.put(keys[i], values[i]);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MyThread(i * (count / THREAD_COUNT));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long end = System.currentTimeMillis();

        log.info("cost time: {}", end - start);
        log.info("{}", m.size());

        // -----------------------------

        start = System.currentTimeMillis();
    }
}
