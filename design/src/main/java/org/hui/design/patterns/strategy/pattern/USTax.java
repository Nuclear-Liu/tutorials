package org.hui.design.patterns.strategy.pattern;

import java.util.logging.Logger;

/**
 * 一种税法算法（美国）.
 */
public class USTax implements TaxStrategy {
    @Override
    public void calculate() {
        Logger.getGlobal().info("USTax...");
    }
}
