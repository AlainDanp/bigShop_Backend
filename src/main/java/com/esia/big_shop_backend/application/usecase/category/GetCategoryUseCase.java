package com.esia.big_shop_backend.application.usecase.category;

import com.esia.big_shop_backend.application.usecase.category.query.GetCategoryQuery;
import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category execute(GetCategoryQuery query) {
        return categoryRepository.findById(CategoryId.of(query.getCategoryId()))
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + query.getCategoryId()));
    }
}
