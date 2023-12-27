package org.hui.authorization.services;

import org.hui.authorization.Order;

import java.io.Serializable;

public class AuthorizationService implements Serializable {
    public boolean authorize(Order order) {
        // In a real scenario, this would call an external service to verify the credit card number for the order.
        // 在实际场景中，这将调用外部服务来验证订单的信用卡号。
        return true;
    }
}
