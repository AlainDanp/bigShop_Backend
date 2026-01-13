package com.esia.big_shop_backend.infrastrucute.payment;

import com.esia.big_shop_backend.application.port.output.MtnPaymentPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MtnPaymentAdapter implements MtnPaymentPort {

    private static final Logger logger = LoggerFactory.getLogger(MtnPaymentAdapter.class);


    @Override
    public String requestPayment(BigDecimal amount, String currency, String payerMsisdn, String reference) {
        logger.info("Requesting MTN payment: amount={}, currency={}, payerMsisdn={}, reference={}",
                amount, currency, payerMsisdn, reference);
        return "MOCK_MTN_TRANSACTION_ID_12345";
    }

    @Override
    public String getStatus(String transactionId) {
        logger.info("Getting MTN payment status for transactionId={}", transactionId);
        return "COMPLETED";
    }

    @Override
    public void refund(String transactionId) {
        logger.info("Refunding MTN payment for transactionId={}", transactionId);

    }
}
