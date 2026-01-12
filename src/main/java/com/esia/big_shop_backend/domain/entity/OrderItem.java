package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderItemId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItem {
    private final OrderItemId id;
    private final ProductId productId;
    private final String productName; // Snapshot at time of order
    private final Money unitPrice; // Price at time of order
    private int quantity;
    private int subtotal;
    private double totalPrice;
}
