package com.esia.big_shop_backend.application.usecase.admin.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetLowStockProductsQuery {
    private int threshold = 10;
    private int page = 0;
    private int size = 20;
}
