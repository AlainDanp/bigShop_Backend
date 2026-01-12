package com.esia.big_shop_backend.infrastrucute.payment.mapper;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.infrastrucute.payment.dto.OrangePaymentRequest;

public final class OrangePaymentMapper {

    private OrangePaymentMapper() {}

    public static OrangePaymentRequest toRequest(Money money, String customerInfo) {
        String currency = money.getCurrency() == null ? "XAF" : money.getCurrency();

        return new OrangePaymentRequest(
                String.valueOf(money.getAmount()),
                currency,
                customerInfo
        );
    }
}
