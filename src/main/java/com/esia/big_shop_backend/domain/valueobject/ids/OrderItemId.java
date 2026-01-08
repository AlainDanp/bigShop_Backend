package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderItemId {
    private final Long value;

    private OrderItemId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("OrderItemId must be a positive number");
        }
        this.value = value;
    }

    public static OrderItemId of(Long value) {
        return new OrderItemId(value);
    }
}
