package com.esia.big_shop_backend.infrastrucute.payment.mapper;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.infrastrucute.payment.dto.MtnPaymentRequest;

public final class MtnPaymentMapper {

    private MtnPaymentMapper() {}

    public static MtnPaymentRequest toRequest(Money money, String customerInfo) {
        String currency = money.getCurrency() == null ? "XAF" : money.getCurrency();

        return new MtnPaymentRequest(
                String.valueOf(money.getAmount()),
                currency,
                customerInfo
        );
    }
}
