package org.hui.design.patterns.strategy.pattern;

import java.util.logging.Logger;

/**
 * 税法计算.
 */
public class SalesOrder {
    private TaxStrategy strategy; // 多态
//    public SalesOrder(StrategyFactory strategyFactory) {
//        strategy = strategyFactory.getStrategy(); // 工厂方法
//    }
    public void calculateTex() {
        Logger.getGlobal().info("SalesOrder.calculateTex() start.");
        strategy.calculate(); // 多态调用
        Logger.getGlobal().info("SalesOrder.calculateTex() end.");
    }
}
