package com.esia.big_shop_backend.application.usecase.category;

import com.esia.big_shop_backend.application.usecase.category.command.CreateCategoryCommand;
import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category execute(CreateCategoryCommand command) {
        // Verify name doesn't already exist
        if (categoryRepository.existsByName(command.getName())) {
            throw new IllegalArgumentException("Category with name '" + command.getName() + "' already exists");
        }

        Category category = new Category(
                null,
                command.getName(),
                command.getDescription(),
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return categoryRepository.save(category);
    }
}
