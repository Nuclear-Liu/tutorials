package org.hui.design.patterns.singleton.pattern.multithreading.volatilekeyword;

public class Singleton {
    private Singleton() {
    }
    private volatile Singleton singleton;

    /**
     * 双检查锁，但由于内存读写 reorder 不安全.
     * reorder:
     *      代码在指令层次的执行次序并不一定是期望的顺序（乱序执行）。
     *
     * @return
     */
    public Singleton getInstance() {
        if (null == singleton) {
            synchronized (this) {
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
