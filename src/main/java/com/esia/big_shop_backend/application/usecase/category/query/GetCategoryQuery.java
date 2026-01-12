package com.esia.big_shop_backend.application.usecase.category.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCategoryQuery {
    private final Long categoryId;
}
