package org.hui.java.concurrency.multithreading.threadapi;

public class ThreadGroupPriority {
    public static void main(String[] args) {
        // 定义一个线程组
        ThreadGroup group = new ThreadGroup("test");
        // 将线程组的优先级指定为 7
        group.setMaxPriority(7);
        // 定义一个线程，将该线程加入到 group 中
        Thread thread = new Thread(group, "test-thread");
        // 企图将线程的优先级设定为 10
        thread.setPriority(10);
        // 企图未遂
        System.out.println(thread.getPriority());
    }
}
