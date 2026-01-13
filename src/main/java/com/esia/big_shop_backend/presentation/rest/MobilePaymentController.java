package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.payment.ProcessMtnPaymentUseCase;
import com.esia.big_shop_backend.application.usecase.payment.ProcessOrangeMoneyPaymentUseCase;
import com.esia.big_shop_backend.application.usecase.payment.command.ProcessMtnPaymentCommand;
import com.esia.big_shop_backend.application.usecase.payment.command.ProcessOrangeMoneyPaymentCommand;
import com.esia.big_shop_backend.presentation.dto.request.payment.MtnPaymentRequest;
import com.esia.big_shop_backend.presentation.dto.request.payment.OrangeMoneyPaymentRequest;
import com.esia.big_shop_backend.presentation.dto.response.payment.PaymentResponse;
import com.esia.big_shop_backend.presentation.mapper.PaymentRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/mobile-payment")
@RequiredArgsConstructor
public class MobilePaymentController {

    private final ProcessMtnPaymentUseCase processMtnPaymentUseCase;
    private final ProcessOrangeMoneyPaymentUseCase processOrangeMoneyPaymentUseCase;
    private final PaymentRestMapper mapper;

    @PostMapping("/mtn")
    public ResponseEntity<PaymentResponse> payMtn(@RequestBody @Valid MtnPaymentRequest req, Principal principal) {
        var payment = processMtnPaymentUseCase.execute(ProcessMtnPaymentCommand.builder()
                .userEmail(principal.getName())
                .orderId(req.getOrderId())
                .amount(req.getAmount())
                .currency(req.getCurrency())
                .payerMsisdn(req.getPayerMsisdn())
                .reference(req.getReference())
                .build());
        return ResponseEntity.ok(mapper.toResponse(payment));
    }

    @PostMapping("/orange")
    public ResponseEntity<PaymentResponse> payOrange(@RequestBody @Valid OrangeMoneyPaymentRequest req, Principal principal) {
        var payment = processOrangeMoneyPaymentUseCase.execute(ProcessOrangeMoneyPaymentCommand.builder()
                .userEmail(principal.getName())
                .orderId(req.getOrderId())
                .amount(req.getAmount())
                .currency(req.getCurrency())
                .payerMsisdn(req.getPayerMsisdn())
                .reference(req.getReference())
                .build());
        return ResponseEntity.ok(mapper.toResponse(payment));
    }
}
