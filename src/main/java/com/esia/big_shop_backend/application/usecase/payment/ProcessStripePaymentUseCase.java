package com.esia.big_shop_backend.application.usecase.payment;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.port.output.StripePaymentPort;
import com.esia.big_shop_backend.application.usecase.payment.command.ProcessStripePaymentCommand;
import com.esia.big_shop_backend.domain.entity.Payment;
import com.esia.big_shop_backend.domain.event.PaymentProcessedEvent;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.enums.PaymentStatus;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.PaymentId;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProcessStripePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final StripePaymentPort stripePaymentPort;
    private final CurrentUserPort currentUserPort;
    private final EventPublisher eventPublisher;

    @Transactional
    public StripePaymentPort.CreateIntentResult execute(ProcessStripePaymentCommand cmd) {
        Long userId = currentUserPort.getUserIdByEmail(cmd.getUserEmail());

        String currency = cmd.getCurrency();
        if (currency == null || currency.isEmpty()) {
            currency = "EUR";
        }

        StripePaymentPort.CreateIntentResult intent =
                stripePaymentPort.createPaymentIntent(cmd.getAmount(), currency, cmd.getDescription());

        Payment payment = Payment.builder()
                .orderId(cmd.getOrderId())
                .userId(userId)
                .amount(cmd.getAmount())
                .currency(currency)
                .provider("STRIPE")
                .providerRef(intent.paymentIntentId())
                .status(PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // Publish event (Note: Status is PENDING here, usually we publish when confirmed via webhook,
        // but for this exercise we publish the initiation)
        eventPublisher.publish(PaymentProcessedEvent.of(
                PaymentId.of(savedPayment.getId()),
                OrderId.of(savedPayment.getOrderId()),
                new Money(savedPayment.getAmount().doubleValue(), savedPayment.getCurrency()),
                savedPayment.getStatus().name(),
                "STRIPE"
        ));

        return intent;
    }
}
