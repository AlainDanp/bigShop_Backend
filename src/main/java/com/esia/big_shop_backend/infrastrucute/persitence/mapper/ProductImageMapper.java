package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.ProductImage;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductImageId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.ProductImageJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    public ProductImage toDomain(ProductImageJpaEntity entity) {
        if (entity == null) return null;

        return new ProductImage(
                entity.getId() != null ? ProductImageId.of(entity.getId()) : null,
                ProductId.of(entity.getProductId()),
                entity.getImageUrl(),
                entity.getIsPrimary()
        );
    }

    public ProductImageJpaEntity toJpaEntity(ProductImage domain) {
        if (domain == null) return null;

        return new ProductImageJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getProductId().getValue(),
                domain.getImageUrl(),
                domain.isPrimary(),
                null, // createdAt will be set by @PrePersist
                null  // updatedAt will be set by @PrePersist
        );
    }
}
