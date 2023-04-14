package org.hui.java.concurrency.multithreading.threadapi;

import java.util.stream.IntStream;

public class ThreadYield {
    public static void main(String[] args) {
        IntStream.range(0, 2).mapToObj(ThreadYield::create).forEach(Thread::start);
    }
    private static Thread create(int index) {
        return new Thread(() -> {
            // 注释部分
            if (index == 0) {
                Thread.yield();
            }
            System.out.println(index);
        });
    }
}
