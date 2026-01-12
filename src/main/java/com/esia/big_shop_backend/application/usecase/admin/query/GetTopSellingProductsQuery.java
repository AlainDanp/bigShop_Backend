package com.esia.big_shop_backend.application.usecase.admin.query;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTopSellingProductsQuery {
    private int limit = 10;
}
