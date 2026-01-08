package com.esia.big_shop_backend.application.usecase.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddProductImageCommand {
    private final Long productId;
    private final String imageUrl;
    private final boolean isPrimary;
}
