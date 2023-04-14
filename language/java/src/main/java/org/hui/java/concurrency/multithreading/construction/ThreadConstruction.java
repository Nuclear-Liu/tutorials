package org.hui.java.concurrency.multithreading.construction;

public class ThreadConstruction {
    public static void main(String[] args) {
        // 1
        Thread t1 = new Thread("t1");
        // 2
        ThreadGroup group = new ThreadGroup("TestGroup");
        // 3
        Thread t2 = new Thread(group, "t2");
        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("Main thread belong group:" + mainThreadGroup.getName());
        System.out.println("t1 and main belong the same group:" + (mainThreadGroup == t1.getThreadGroup()));
        System.out.println("t2 thread group not belong main group:" + (mainThreadGroup == t2.getThreadGroup()));
        System.out.println("t2 thread group belong main TestGroup:" + (group == t2.getThreadGroup()));
    }
}
