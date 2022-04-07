package org.hui.java.concurrencyprogramming;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
public class ThreadInterrupted {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (; ; ) {
                if (Thread.interrupted()) {
                    log.info("Thread is interrupted.");
                    log.info("{} current interrupt: {}", Thread.currentThread().getName(), Thread.interrupted());
                }
            }
        });

        t.start();

        SleepHelper.sleepSeconds(2);

        t.interrupt();

        // 思考以下，如果这里写
        log.info("Main: {}", t.interrupted());
        // 输出的是哪个线程的中断状态
    }
}
