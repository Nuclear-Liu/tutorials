package org.hui.design.patterns.templatemethod.pattern;

import java.util.logging.Logger;

/**
 * 程序库开发人员
 */
public abstract class Library {
    /**
     * 稳定 template method.
     *
     * 稳定中有变化：稳定的非虚函数，变化的虚函数
     */
    public void run() {
        Logger.getGlobal().info("Library:run() start.");
        step1();

        if (step2()) { // 多态调用
            step3();
        }

        for (int i = 0; i < 4; i++) {
            step4(); // 多态调用
        }

        step5();
        Logger.getGlobal().info("Library:run() end.");
    }

    protected void step1() { // 稳定
        Logger.getGlobal().info("Library:step1().");
        // ...
    }

    protected void step3() { // 稳定
        Logger.getGlobal().info("Library:step3()");
        // ...
    }

    protected void step5() { // 稳定
        Logger.getGlobal().info("Library:step5()");
        // ...
    }

    protected abstract boolean step2(); // 变化

    protected abstract void step4(); // 变化
}
