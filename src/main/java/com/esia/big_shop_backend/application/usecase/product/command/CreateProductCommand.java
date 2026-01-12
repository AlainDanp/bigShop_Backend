package com.esia.big_shop_backend.application.usecase.product.command;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductCommand {
    private final String name;
    private final String description;
    private final Double price;
    private final Double discountPrice;
    private final Integer stockQuantity;
    private final Long categoryId;
}
