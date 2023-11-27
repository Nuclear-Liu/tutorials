package org.hui.recommendations.services;

import org.hui.recommendations.domain.Sale;

import java.util.Date;

class FlashSaleService {
    private final LatencySimulator latency = new LatencySimulator(50, 50, 100, 200, 5);

    public Sale lookupSale(String saleId, int timeoutInMillis) {
        latency.simulate(timeoutInMillis);

        return new Sale(buildProductName(saleId), new Date());
    }

    private String buildProductName(String saleId) {
        return "product-" + saleId;
    }
}
