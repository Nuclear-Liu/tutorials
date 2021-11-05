package org.hui.java.concurrency.multithreading.threadapi.shutdown;

import java.util.concurrent.TimeUnit;

public class FlagThreadExit {
    static class MyTask extends Thread {
        private volatile boolean closed = false;

        @Override
        public void run() {
            System.out.println("I will start work");
            while (!closed && !isInterrupted()) {
                // 正在运行
            }
            System.out.println("I will be exiting.");
        }

        public void close() {
            closed = true;
            interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTask t = new MyTask();
        t.start();
        TimeUnit.SECONDS.sleep(6);
        System.out.println("System will be shutdown.");
        t.close();
    }
}
