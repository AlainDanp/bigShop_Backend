package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderItemId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItem {
    private final OrderItemId id;
    private final ProductId productId;
    private final String productName; // Snapshot at time of order
    private final Money unitPrice; // Price at time of order
    private int quantity;

    public Money getSubtotal() {
        return unitPrice.multiply(quantity);
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = newQuantity;
    }
}
