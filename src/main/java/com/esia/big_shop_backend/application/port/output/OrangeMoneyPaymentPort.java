package com.esia.big_shop_backend.application.port.output;

import java.math.BigDecimal;

public interface OrangeMoneyPaymentPort {
    String requestPayment(BigDecimal amount, String currency, String payerMsisdn, String reference);
    String getStatus(String transactionId);
    void refund(String transactionId);
}
