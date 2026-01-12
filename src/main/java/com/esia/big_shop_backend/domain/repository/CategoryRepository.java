package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(CategoryId id);
    List<Category> findAll(int pageable);
    List<Category> findRootCategories(int page);
    List<Category> findSubCategories(CategoryId parentId, int page);
    void deleteById(CategoryId id);
    boolean existsById(CategoryId id);
    boolean existsByName(String name);
}
