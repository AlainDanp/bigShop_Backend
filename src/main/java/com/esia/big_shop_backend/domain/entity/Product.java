package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private final ProductId id;
    private String name;
    private String description;
    private Money price;
    private Money discountPrice;
    private Integer stockQuantity;
    private CategoryId categoryId;
    private boolean active;
}
