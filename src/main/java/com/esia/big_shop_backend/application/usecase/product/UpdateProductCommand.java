package com.esia.big_shop_backend.application.usecase.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Double discountPrice;
    private Integer stockQuantity;
    private Long categoryId;
}
