package org.hui.design.patterns.template_method.pattern;

import java.util.logging.Logger;

/**
 * 应用程序开发人员.
 */
public class Application extends Library{
    @Override
    protected boolean step2() {
        Logger.getGlobal().info("Application:step2()");
        // ... 子类重写实现 支持变化
        return false;
    }

    @Override
    protected void step4() {
        Logger.getGlobal().info("Application:step4()");
        // ... 子类重写实现 支持变化
    }

    public static void main(String[] args) {
        Library lib = new Application(); // 多态
        lib.run();
    }
}
