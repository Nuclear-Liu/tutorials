package org.hui.design.patterns.templatemethod.motivation;

import java.util.logging.Logger;

/**
 * 应用程序开发人员.
 */
public class Application {
    public boolean step2() {
        Logger.getGlobal().info("Application:step2().");
        // ...
        return true;
    }
    public void step4() {
        Logger.getGlobal().info("Application:step4().");
        // ...
    }

    public static void main(String[] args) {
        Library lib = new Library();
        Application app = new Application();

        Logger.getGlobal().info("run() start.");
        lib.step1();
        if (app.step2()) {
            lib.step3();
        }
        for (int i = 0; i < 4; i++) {
            app.step4();
        }
        lib.step5();
        Logger.getGlobal().info("run() end.");
    }
}
