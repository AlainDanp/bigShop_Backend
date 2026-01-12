package com.esia.big_shop_backend.application.usecase.payment;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.port.output.MtnPaymentPort;
import com.esia.big_shop_backend.application.usecase.payment.command.ProcessMtnPaymentCommand;
import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentStatus;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class ProcessMtnPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final MtnPaymentPort mtnPaymentPort;
    private final CurrentUserPort currentUserPort;

    public Payment execute(ProcessMtnPaymentCommand cmd) {
        Long userId = currentUserPort.getUserIdByEmail(cmd.getUserEmail());

        String txId = mtnPaymentPort.requestPayment(
                cmd.getAmount(), cmd.getCurrency(), cmd.getPayerMsisdn(), cmd.getReference()
        );

        Payment payment = Payment.builder()
                .orderId(cmd.getOrderId())
                .userId(userId)
                .amount(cmd.getAmount())
                .currency(cmd.getCurrency())
                .provider("MTN")
                .providerRef(txId)
                .status(PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return paymentRepository.save(payment);
    }
}
