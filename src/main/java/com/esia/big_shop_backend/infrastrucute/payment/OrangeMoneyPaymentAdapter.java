package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.OrangeMoneyPaymentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrangeMoneyPaymentAdapter implements OrangeMoneyPaymentPort {

    private static final Logger logger = LoggerFactory.getLogger(OrangeMoneyPaymentAdapter.class);

    @Override
    public String requestPayment(BigDecimal amount, String currency, String payerMsisdn, String reference) {
        logger.info("Requesting Orange Money payment: amount={}, currency={}, payerMsisdn={}, reference={}",
                amount, currency, payerMsisdn, reference);
        return "OM_TXN_" + System.currentTimeMillis();
    }

    @Override
    public String getStatus(String transactionId) {
        return "PENDING";
    }

    @Override
    public void refund(String transactionId) {
        logger.info("Refunding Orange Money transaction: transactionId={}", transactionId);
    }
}
