package org.hui.design.patterns.strategy.motivation;

import java.util.logging.Logger;

/**
 * 税法计算.
 */
public class SalesOrder {
    TaxBase tax;
    public void calculateTax() {
        Logger.getGlobal().info("SalesOrder.calculateTax() start.");
        // ...
        if (tax == TaxBase.CN_Tax) {
            Logger.getGlobal().info("CN_Tax...");
        } else if (tax == TaxBase.US_Tax) {
            Logger.getGlobal().info("US_Tax...");
        } else if (tax == TaxBase.DE_Tax) {
            Logger.getGlobal().info("DE_Tex...");
        } else if (tax == TaxBase.FR_Tax) { // 更改
            Logger.getGlobal().info("FR_Tax...");
        }
        // ...
        Logger.getGlobal().info("SalesOrder.calculateTax() end.");
    }
}
