package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductImageId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductImage {
    private final ProductImageId id;
    private final ProductId productId;
    private final String imageUrl;
    private final boolean isPrimary;

    public void markAsPrimary() {
        // This will be handled at the use case level to ensure only one primary image per product
    }
}
