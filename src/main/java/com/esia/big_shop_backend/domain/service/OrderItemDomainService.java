package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.OrderItem;
import com.esia.big_shop_backend.domain.valueobject.Money;
import org.springframework.stereotype.Service;

@Service
public class OrderItemDomainService {

    public Money calculateSubtotal(OrderItem orderItem) {
        return orderItem.getUnitPrice().multiply(orderItem.getQuantity());
    }

    public void updateQuantity(OrderItem orderItem, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        orderItem.setQuantity(newQuantity);
    }

    public void increaseQuantity(OrderItem orderItem, int additionalQuantity) {
        if (additionalQuantity <= 0) {
            throw new IllegalArgumentException("Additional quantity must be positive");
        }
        orderItem.setQuantity(orderItem.getQuantity() + additionalQuantity);
    }

    public void decreaseQuantity(OrderItem orderItem, int reductionQuantity) {
        if (reductionQuantity <= 0) {
            throw new IllegalArgumentException("Reduction quantity must be positive");
        }
        int newQuantity = orderItem.getQuantity() - reductionQuantity;
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Resulting quantity must be positive. Current: "
                    + orderItem.getQuantity() + ", reduction: " + reductionQuantity);
        }
        orderItem.setQuantity(newQuantity);
    }

    public Money getTotalPrice(OrderItem orderItem) {
        return calculateSubtotal(orderItem);
    }
}
