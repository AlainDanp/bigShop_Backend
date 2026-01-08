package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CartId {
    private final Long value;

    private CartId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("CartId must be a positive number");
        }
        this.value = value;
    }

    public static CartId of(Long value) {
        return new CartId(value);
    }
}
