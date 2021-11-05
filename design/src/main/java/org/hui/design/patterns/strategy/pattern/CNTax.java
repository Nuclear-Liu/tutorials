package org.hui.design.patterns.strategy.pattern;

import java.util.logging.Logger;

/**
 * 一种税法算法（中国）.
 */
public class CNTax implements TaxStrategy{
    @Override
    public void calculate() {
        Logger.getGlobal().info("CNTax...");
    }
}
