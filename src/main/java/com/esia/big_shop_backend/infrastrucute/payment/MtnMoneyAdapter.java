package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.MtnPaymentPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MtnMoneyAdapter implements MtnPaymentPort {

    @Override
    public String requestPayment(BigDecimal amount, String currency, String payerMsisdn, String reference) {
        return "MTN-" + UUID.randomUUID();
    }

    @Override
    public String getStatus(String transactionId) {
        return "PENDING";
    }

    @Override
    public void refund(String transactionId) {
    }
}
