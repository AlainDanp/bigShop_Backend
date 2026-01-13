package com.esia.big_shop_backend.presentation.dto.request.order;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderRequest {

    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;
}
