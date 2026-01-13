package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.domain.entity.ProductImage;
import com.esia.big_shop_backend.presentation.dto.response.product.ProductImageResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductImageRestMapper {

    public ProductImageResponse toResponse(ProductImage image) {
        return ProductImageResponse.builder()
                .id(image.getId() != null ? image.getId().getValue() : null)
                .productId(image.getProductId() != null ? image.getProductId().getValue() : null)
                .imageUrl(image.getImageUrl())
                .isPrimary(image.isPrimary())
                .build();
    }
}
