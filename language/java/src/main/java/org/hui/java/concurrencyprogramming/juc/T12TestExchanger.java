package org.hui.java.concurrencyprogramming.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

@Slf4j
public class T12TestExchanger {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(() -> {
            String s = "T1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                log.info("{} interrupted.", Thread.currentThread().getName(), e);
            }
            log.info("{} {}", Thread.currentThread().getName(), s);
        }, "t1").start();

        new Thread(() -> {
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                log.info("{} interrupted", Thread.currentThread().getName());
            }
            log.info("{} {}", Thread.currentThread().getName(), s);
        }, "t2").start();
    }
}
