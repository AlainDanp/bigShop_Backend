package com.esia.big_shop_backend.application.usecase.product;


import lombok.Getter;

@Getter
public class UpdateProductCommand {
    Long productId;
    String name;
    String description;
    Double price;
    Double discountPrice;
    Integer stockQuantity;
    Long categoryId;
    String brand;
    Boolean isActive;
}
