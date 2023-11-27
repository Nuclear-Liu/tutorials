package org.hui.recommendations.services;

import org.hui.recommendations.domain.Sale;

public class FlashSaleClient {
    private final int timeoutInMillis;
    private final FlashSaleService stubService;
    public FlashSaleClient(int timeoutInMillis) {
        this.timeoutInMillis = timeoutInMillis;
        this.stubService = new FlashSaleService();
    }
    public Sale lookupSale(String saleId) {
        return stubService.lookupSale(saleId, timeoutInMillis);
    }
}
