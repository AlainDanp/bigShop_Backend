package com.esia.big_shop_backend.infrastrucute.persitence.mapper;

import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CategoryJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toDomain(CategoryJpaEntity entity) {
        if (entity == null) return null;

        return new Category(
                entity.getId() != null ? CategoryId.of(entity.getId()) : null,
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CategoryJpaEntity toJpaEntity(Category domain) {
        if (domain == null) return null;

        return new CategoryJpaEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getName(),
                domain.getDescription(),
                domain.isActive(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
