package org.hui.design.patterns.singleton.pattern.multithreading.singlechecklock;

public class Singleton {
    private Singleton() {
    }
    private Singleton singleton;

    /**
     * 线程安全版本，锁代价过高.
     * @return
     */
    public synchronized Singleton getInstance() {
        if (null == singleton) {
            singleton = new Singleton();
        }
        return singleton;
    }

}
