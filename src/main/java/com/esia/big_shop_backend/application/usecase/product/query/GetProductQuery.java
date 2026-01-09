package com.esia.big_shop_backend.application.usecase.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetProductQuery {
    private final Long productId;
}
