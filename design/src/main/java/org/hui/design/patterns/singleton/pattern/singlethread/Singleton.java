package org.hui.design.patterns.singleton.pattern.singlethread;

public class Singleton {
    private Singleton() {
    }
    private Singleton singleton;

    /**
     * 线程非安全版本.
     * @return
     */
    public Singleton getInstance() {
        if (null == singleton) {
            singleton = new Singleton();
        }
        return singleton;
    }


}
