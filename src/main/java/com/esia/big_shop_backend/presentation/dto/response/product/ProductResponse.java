package com.esia.big_shop_backend.presentation.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer stockQuantity;
    private Long categoryId;
    private String categoryName;
    private Boolean isActive;
    private Boolean hasDiscount;
}
