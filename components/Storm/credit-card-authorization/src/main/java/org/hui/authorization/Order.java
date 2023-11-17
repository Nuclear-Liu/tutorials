package org.hui.authorization;

import java.io.Serializable;

public class Order implements Serializable {
    private long id;
    private long customerId;
    private long creditCardNumber;
    private int creditCardCode;
    private double chargeAmount;

    public Order(long id, long customerId, long creditCardNumber, int creditCardCode, double chargeAmount) {
        this.id = id;
        this.customerId = customerId;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCode = creditCardCode;
        this.chargeAmount = chargeAmount;
    }

    @Override
    public String toString() {
        return "Orider{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", creditCardNumber=" + creditCardNumber +
                ", creditCardCode=" + creditCardCode +
                ", chargeAmount=" + chargeAmount +
                '}';
    }
}
