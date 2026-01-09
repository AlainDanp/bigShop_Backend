package com.esia.big_shop_backend.application.port.output;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentMethod;

public interface PaymentPort {
    String processPayment(Money amount, PaymentMethod method, String customerInfo);
    boolean verifyPayment(String paymentId);
    void refundPayment(String paymentId, Money amount);
    String getPaymentStatus(String paymentId);
}
