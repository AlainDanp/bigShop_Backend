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
                entity.getParent() != null ? entity.getParent().getId() : null,
                entity.getName(),
                entity.getDescription(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CategoryJpaEntity toJpaEntity(Category domain) {
        if (domain == null) return null;

        CategoryJpaEntity entity = new CategoryJpaEntity();
        entity.setId(domain.getCategoryId() != null ? domain.getCategoryId().getValue() : null);
        
        if (domain.getParentId() != null) {
            CategoryJpaEntity parent = new CategoryJpaEntity();
            parent.setId(domain.getParentId());
            entity.setParent(parent);
        }

        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setIsActive(domain.isActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        
        return entity;
    }
}
