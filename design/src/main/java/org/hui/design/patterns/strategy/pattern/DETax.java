package org.hui.design.patterns.strategy.pattern;

import java.util.logging.Logger;
/**
 * 一种税法算法（德国）.
 */
public class DETax implements TaxStrategy {
    @Override
    public void calculate() {
        Logger.getGlobal().info("DETax...");
    }
}
