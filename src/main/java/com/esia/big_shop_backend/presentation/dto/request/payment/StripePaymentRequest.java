package com.esia.big_shop_backend.presentation.dto.request.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StripePaymentRequest {
    @NotNull private Long orderId;
    @NotNull private BigDecimal amount;
    @NotBlank private String currency;
    private String description;
}
