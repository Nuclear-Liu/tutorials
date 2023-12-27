package org.hui.recommendations.domain;

import java.io.Serializable;
import java.util.Date;

public class Sale implements Serializable {
    private final String productName;
    private final Date starts;

    public Sale(String productName, Date starts) {
        this.productName = productName;
        this.starts = starts;
    }
    @Override
    public String toString() {
        return productName + " on " + starts.toString();
    }
}
