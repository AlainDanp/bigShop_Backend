package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ParentId {
    private final Long value;
    
    private ParentId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("ParentId must be a positive number");
        }
        this.value = value;
    }
}
