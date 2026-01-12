package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.OrangeMoneyPaymentPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrangeMoneyAdapter implements OrangeMoneyPaymentPort {

    @Override
    public String requestPayment(BigDecimal amount, String currency, String payerMsisdn, String reference) {
        return "OM-" + UUID.randomUUID();
    }

    @Override
    public String getStatus(String transactionId) {
        return "PENDING";
    }

    @Override
    public void refund(String transactionId) {
    }
}
