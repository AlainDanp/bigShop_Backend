package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class RoleId {
    private final Long value;

    private RoleId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("RoleId must be a positive number");
        }
        this.value = value;
    }

    public static RoleId of(Long value) {
        return new RoleId(value);
    }
}
