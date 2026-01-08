package com.esia.big_shop_backend.domain.valueobject.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PaymentId {
    private final Long value;

    private PaymentId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("PaymentId must be a positive number");
        }
        this.value = value;
    }

    public static PaymentId of(Long value) {
        return new PaymentId(value);
    }
}
