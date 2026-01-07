package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class ProductId {
    private final Long value;

    public ProductId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        this.value = value;
    }

    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}
