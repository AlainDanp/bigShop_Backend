package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CartItemId {
    private final Long value;

    private CartItemId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("CartItemId must be a positive number");
        }
        this.value = value;
    }

    public static CartItemId of(Long value) {
        return new CartItemId(value);
    }
}
