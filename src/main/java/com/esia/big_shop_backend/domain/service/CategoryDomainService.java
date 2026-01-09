package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryDomainService {

    public void updateDetails(Category category, String name, String description) {
        if (name != null && !name.isBlank()) {
            category.setName(name.trim());
        }
        if (description != null) {
            category.setDescription(description.trim());
        }
        category.setUpdatedAt(LocalDateTime.now());
    }

    public void updateName(Category category, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        category.setName(name.trim());
        category.setUpdatedAt(LocalDateTime.now());
    }

    public void updateDescription(Category category, String description) {
        category.setDescription(description != null ? description.trim() : null);
        category.setUpdatedAt(LocalDateTime.now());
    }

    public void activate(Category category) {
        category.setActive(true);
        category.setUpdatedAt(LocalDateTime.now());
    }

    public void deactivate(Category category) {
        category.setActive(false);
        category.setUpdatedAt(LocalDateTime.now());
    }

    public boolean isActive(Category category) {
        return category.isActive();
    }
}
