package com.esia.big_shop_backend.application.usecase.category;

import com.esia.big_shop_backend.application.usecase.category.command.UpdateCategoryCommand;
import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import com.esia.big_shop_backend.domain.service.CategoryDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final CategoryDomainService categoryDomainService;

    @Transactional
    public Category execute(UpdateCategoryCommand command) {
        Category category = categoryRepository.findById(CategoryId.of(command.getCategoryId()))
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + command.getCategoryId()));

        categoryDomainService.updateDetails(category, command.getName(), command.getDescription());

        return categoryRepository.save(category);
    }
}
