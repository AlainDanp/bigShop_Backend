package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.Money;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.ProductJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductJpaEntity entity) {
        if (entity == null) return null;

        return new Product(
                entity.getId() != null ? ProductId.of(entity.getId()) : null,
                entity.getName(),
                entity.getDescription(),
                new Money(entity.getPrice(), "XAF"),
                entity.getDiscountPrice() != null ? new Money(entity.getDiscountPrice(), "XAF") : null,
                entity.getStockQuantity(),
                CategoryId.of(entity.getCategoryId()),
                entity.getIsActive()
        );
    }

    public ProductJpaEntity toJpaEntity(Product domain) {
        if (domain == null) return null;

        return new ProductJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getName(),
                domain.getDescription(),
                domain.getPrice().getAmount(),
                domain.getDiscountPrice() != null ? domain.getDiscountPrice().getAmount() : null,
                domain.getStockQuantity(),
                domain.getCategoryId().getValue(),
                domain.isActive(),
                null // category relation
        );
    }
}
