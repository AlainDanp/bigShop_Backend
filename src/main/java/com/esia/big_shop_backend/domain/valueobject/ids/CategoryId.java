package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CategoryId {
    private final Long value;

    private CategoryId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("CategoryId must be a positive number");
        }
        this.value = value;
    }

    public static CategoryId of(Long value) {
        return new CategoryId(value);
    }
}
