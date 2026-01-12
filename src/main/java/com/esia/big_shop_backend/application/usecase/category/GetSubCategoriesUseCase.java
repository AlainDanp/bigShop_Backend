package com.esia.big_shop_backend.application.usecase.category;

import com.esia.big_shop_backend.application.usecase.category.query.GetSubCategoriesQuery;
import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSubCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> execute(GetSubCategoriesQuery query) {
        return categoryRepository.findSubCategories(CategoryId.of(query.getParentCategoryId()), query.getPage());
    }
}
