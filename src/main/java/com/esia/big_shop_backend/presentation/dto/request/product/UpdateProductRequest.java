package com.esia.big_shop_backend.presentation.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;
    private Double discountPrice;
    private Integer stockQuantity;
}
