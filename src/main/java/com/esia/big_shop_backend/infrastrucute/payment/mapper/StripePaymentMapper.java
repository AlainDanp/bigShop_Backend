package com.esia.big_shop_backend.infrastrucute.payment.mapper;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.infrastrucute.payment.dto.StripePaymentRequest;

public final class StripePaymentMapper {

    private StripePaymentMapper() {}

    public static StripePaymentRequest toRequest(Money money, String description) {
        long amountInCents = Math.round(money.getAmount() * 100);
        String currency = money.getCurrency() == null ? "EUR" : money.getCurrency().toLowerCase();

        return new StripePaymentRequest(
                amountInCents,
                currency,
                description
        );
    }
}
