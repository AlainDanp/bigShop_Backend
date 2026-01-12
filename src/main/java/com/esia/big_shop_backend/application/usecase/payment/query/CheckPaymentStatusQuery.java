package com.esia.big_shop_backend.application.usecase.payment.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckPaymentStatusQuery {
    private final Long paymentId;
}
