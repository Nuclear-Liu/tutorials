package org.hui.design.patterns.template_method.motivation;

import java.util.logging.Logger;

/**
 * 程序库开发人员.
 */
public class Library {
    public void step1() {
        Logger.getGlobal().info("Library.step1().");
        // ...
    }
    public void step3() {
        Logger.getGlobal().info("Library:step3().");
        // ...
    }
    public void step5() {
        Logger.getGlobal().info("Library:step5().");
        // ...
    }
}
