package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ProductImageId {
    private final Long value;

    private ProductImageId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("ProductImageId must be a positive number");
        }
        this.value = value;
    }

    public static ProductImageId of(Long value) {
        return new ProductImageId(value);
    }
}
