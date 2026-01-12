package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllCategoriesQuery {
    private int page = 0;
    private int size = 20;
}
