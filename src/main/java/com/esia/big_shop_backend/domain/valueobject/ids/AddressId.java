package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AddressId {
    private final Long value;

    private AddressId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("AddressId must be a positive number");
        }
        this.value = value;
    }

    public static AddressId of(Long value) {
        return new AddressId(value);
    }
}
