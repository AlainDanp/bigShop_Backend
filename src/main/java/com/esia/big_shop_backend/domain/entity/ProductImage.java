package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductImageId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductImage {
    private final ProductImageId id;
    private final ProductId productId;
    private final String imageUrl;
    private boolean isPrimary;
}
