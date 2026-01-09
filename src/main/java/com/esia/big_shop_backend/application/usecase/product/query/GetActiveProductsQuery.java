package com.esia.big_shop_backend.application.usecase.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetActiveProductsQuery {
    private int page = 0;
    private int size = 20;
}
