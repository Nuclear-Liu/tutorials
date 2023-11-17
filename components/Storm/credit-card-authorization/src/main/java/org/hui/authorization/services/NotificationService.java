package org.hui.authorization.services;

import org.hui.authorization.Order;

import java.io.Serializable;

public class NotificationService implements Serializable {
    public void notifyOrderHasBeenProcessed(Order order) {
        // In a real scenario, this would notify any downstream systems that the order has been processed.
        // 在实际场景中，这将通知任何下游系统订单已处理。
    }
}
