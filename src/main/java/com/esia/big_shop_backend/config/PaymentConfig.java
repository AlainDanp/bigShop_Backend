package com.esia.big_shop_backend.config;

import com.esia.big_shop_backend.application.port.output.*;
import com.esia.big_shop_backend.application.usecase.payment.*;
import com.esia.big_shop_backend.domain.event.PaymentProcessedEvent;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Bean
    public ProcessStripePaymentUseCase processStripePaymentUseCase(
            PaymentRepository paymentRepository,
            StripePaymentPort stripePaymentPort,
            CurrentUserPort currentUserPort,
            EventPublisher eventPublisher) {
        return new ProcessStripePaymentUseCase(paymentRepository, stripePaymentPort, currentUserPort, eventPublisher);
    }

    @Bean
    public ProcessOrangeMoneyPaymentUseCase processOrangeMoneyPaymentUseCase(
            PaymentRepository paymentRepository,
            OrangeMoneyPaymentPort orangeMoneyPaymentPort,
            CurrentUserPort currentUserPort,
            EventPublisher eventPublisher) {
        return new ProcessOrangeMoneyPaymentUseCase(paymentRepository, orangeMoneyPaymentPort, currentUserPort, eventPublisher);
    }

    @Bean
    public ProcessMtnPaymentUseCase processMtnPaymentUseCase(
            PaymentRepository paymentRepository,
            MtnPaymentPort mtnPaymentPort,
            CurrentUserPort currentUserPort,
            EventPublisher eventPublisher) {
        return new ProcessMtnPaymentUseCase(paymentRepository, mtnPaymentPort, currentUserPort, eventPublisher);
    }

    @Bean
    public CheckPaymentStatusUseCase checkPaymentStatusUseCase(
            PaymentRepository paymentRepository,
            StripePaymentPort stripePaymentPort,
            MtnPaymentPort mtnPaymentPort,
            OrangeMoneyPaymentPort orangeMoneyPaymentPort) {
        return new CheckPaymentStatusUseCase(paymentRepository, stripePaymentPort, mtnPaymentPort, orangeMoneyPaymentPort);
    }

    @Bean
    public RefundPaymentUseCase refundPaymentUseCase(
            PaymentRepository paymentRepository,
            StripePaymentPort stripePaymentPort,
            MtnPaymentPort mtnPaymentPort,
            OrangeMoneyPaymentPort orangeMoneyPaymentPort,
            CurrentUserPort currentUserPort) {
        return new RefundPaymentUseCase(paymentRepository, stripePaymentPort, mtnPaymentPort, orangeMoneyPaymentPort, currentUserPort);
    }
}
