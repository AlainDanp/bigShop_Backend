package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.payment.*;
import com.esia.big_shop_backend.application.usecase.payment.command.*;
import com.esia.big_shop_backend.presentation.dto.request.payment.*;
import com.esia.big_shop_backend.presentation.dto.response.payment.PaymentResponse;
import com.esia.big_shop_backend.presentation.mapper.PaymentRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment processing APIs")
public class PaymentController {

    private final ProcessStripePaymentUseCase processStripePaymentUseCase;
    private final ProcessMtnPaymentUseCase processMtnPaymentUseCase;
    private final ProcessOrangeMoneyPaymentUseCase processOrangeMoneyPaymentUseCase;
    private final CheckPaymentStatusUseCase checkPaymentStatusUseCase;
    private final RefundPaymentUseCase refundPaymentUseCase;
    private final PaymentRestMapper mapper;

    @PostMapping("/webhook")
    @Operation(summary = "Stripe webhook endpoint")
    public ResponseEntity<Map<String, String>> webhook(@RequestBody String payload,
                                                       @RequestHeader(name = "Stripe-Signature", required = false) String sig) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
