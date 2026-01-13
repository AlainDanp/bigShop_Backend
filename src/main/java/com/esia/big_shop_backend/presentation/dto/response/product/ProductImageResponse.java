package com.esia.big_shop_backend.presentation.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageResponse {
    private Long id;
    private Long productId;
    private String imageUrl;
    private boolean isPrimary;
}
