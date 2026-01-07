package com.esia.big_shop_backend.application.usecase.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class CreateProductCommand {
    String name;
    String description;
    Double price;
    Double discountPrice;
    Integer stockQuantity;
    Long categoryId;
    String brand;
}
