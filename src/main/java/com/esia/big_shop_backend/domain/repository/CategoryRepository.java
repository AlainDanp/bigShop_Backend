package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(CategoryId id);
    Page<Category> findAll(Pageable pageable);
    void deleteById(CategoryId id);
    boolean existsById(CategoryId id);
    boolean existsByName(String name);
}
