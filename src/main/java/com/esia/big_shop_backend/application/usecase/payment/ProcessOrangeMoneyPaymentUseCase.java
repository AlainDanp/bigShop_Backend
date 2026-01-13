package com.esia.big_shop_backend.application.usecase.payment;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.port.output.OrangeMoneyPaymentPort;
import com.esia.big_shop_backend.application.usecase.payment.command.ProcessOrangeMoneyPaymentCommand;
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
public class ProcessOrangeMoneyPaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final OrangeMoneyPaymentPort orangeMoneyPaymentPort;
    private final CurrentUserPort currentUserPort;
    private final EventPublisher eventPublisher;

    @Transactional
    public Payment execute(ProcessOrangeMoneyPaymentCommand cmd) {
        Long userId = currentUserPort.getUserIdByEmail(cmd.getUserEmail());

        String txId = orangeMoneyPaymentPort.requestPayment(
                cmd.getAmount(), cmd.getCurrency(), cmd.getPayerMsisdn(), cmd.getReference()
        );

        Payment payment = Payment.builder()
                .orderId(cmd.getOrderId())
                .userId(userId)
                .amount(cmd.getAmount())
                .currency(cmd.getCurrency())
                .provider("ORANGE_MONEY")
                .providerRef(txId)
                .status(PaymentStatus.PENDING)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        eventPublisher.publish(PaymentProcessedEvent.of(
                PaymentId.of(savedPayment.getId()),
                OrderId.of(savedPayment.getOrderId()),
                new Money(savedPayment.getAmount().doubleValue(), savedPayment.getCurrency()),
                savedPayment.getStatus().name(),
                "ORANGE_MONEY"
        ));

        return savedPayment;
    }
}
