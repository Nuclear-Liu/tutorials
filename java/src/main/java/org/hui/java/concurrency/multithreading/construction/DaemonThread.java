package org.hui.java.concurrency.multithreading.construction;

public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        // main 线程
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // 将 thread 设置为守护线程
        thread.setDaemon(true);
        thread.start(); // 启动 thread 线程
        Thread.sleep(2_000L);
        System.out.println("Main thread finished lifecycle.");
        // main 线程结束
    }
}
