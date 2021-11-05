package org.hui.design.patterns.strategy.pattern;

import java.util.logging.Logger;

/**
 * 新税发算法（法国）.
 */
public class FRTax implements TaxStrategy {
    @Override
    public void calculate() {
        Logger.getGlobal().info("FRTax...");
    }
}
