package com.esia.big_shop_backend.application.usecase.category;

import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> execute(int page) {
        return categoryRepository.findAll(page);
    }
}
