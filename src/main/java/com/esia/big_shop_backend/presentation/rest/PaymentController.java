package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.payment.*;
import com.esia.big_shop_backend.application.usecase.payment.command.*;
import com.esia.big_shop_backend.presentation.dto.request.payment.*;
import com.esia.big_shop_backend.presentation.dto.response.payment.PaymentResponse;
import com.esia.big_shop_backend.presentation.mapper.PaymentRestMapper;
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

    @PostMapping("/intent")
    public ResponseEntity<PaymentResponse> createStripeIntent(@RequestBody @Valid StripePaymentRequest req,
                                                              Principal principal) {
        var intent = processStripePaymentUseCase.execute(ProcessStripePaymentCommand.builder()
                .userEmail(principal.getName())
                .orderId(req.getOrderId())
                .amount(req.getAmount())
                .currency(req.getCurrency())
                .description(req.getDescription())
                .build());

        return ResponseEntity.ok(PaymentResponse.builder()
                .provider("STRIPE")
                .providerRef(intent.paymentIntentId())
                .clientSecret(intent.clientSecret())
                .status("PENDING")
                .orderId(req.getOrderId())
                .build());
    }

    @PostMapping("/confirm/{paymentIntentId}")
    public ResponseEntity<Map<String, String>> confirmStripe(@PathVariable String paymentIntentId) {
        // TODO: Implement actual confirmation logic if needed, or rely on Webhook
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("paymentIntentId", paymentIntentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrder(@PathVariable Long orderId) {
        var updated = checkPaymentStatusUseCase.execute(orderId);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PostMapping("/refund/{paymentId}")
    public ResponseEntity<PaymentResponse> refund(@PathVariable Long paymentId, Principal principal) {
        var refunded = refundPaymentUseCase.execute(RefundPaymentCommand.builder()
                .userEmail(principal.getName())
                .paymentId(paymentId)
                .build());
        return ResponseEntity.ok(mapper.toResponse(refunded));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Map<String, String>> webhook(@RequestBody String payload,
                                                       @RequestHeader(name = "Stripe-Signature", required = false) String sig) {
        // TODO: Implement Stripe Webhook verification and order status update
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

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
