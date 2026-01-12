package com.esia.big_shop_backend.application.usecase.payment;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.port.output.StripePaymentPort;
import com.esia.big_shop_backend.application.usecase.payment.command.ProcessStripePaymentCommand;
import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentStatus;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class ProcessStripePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final StripePaymentPort stripePaymentPort;
    private final CurrentUserPort currentUserPort;

    public StripePaymentPort.CreateIntentResult execute(ProcessStripePaymentCommand cmd) {
        Long userId = currentUserPort.getUserIdByEmail(cmd.getUserEmail());

        StripePaymentPort.CreateIntentResult intent =
                stripePaymentPort.createPaymentIntent(cmd.getAmount(), cmd.getCurrency(), cmd.getDescription());

        Payment payment = Payment.builder()
                .orderId(cmd.getOrderId())
                .userId(userId)
                .amount(cmd.getAmount())
                .currency(cmd.getCurrency())
                .provider("STRIPE")
                .providerRef(intent.paymentIntentId())
                .status(PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        paymentRepository.save(payment);
        return intent;
    }
}
