package com.esia.big_shop_backend.application.usecase.category;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.usecase.category.command.CreateCategoryCommand;
import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.event.CategoryCreatedEvent;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public Category execute(CreateCategoryCommand command) {
        if (categoryRepository.existsByName(command.getName())) {
            throw new IllegalArgumentException("Category with name '" + command.getName() + "' already exists");
        }
        Category category = new Category(
                null,
                command.getParentId() != null ? command.getParentId() : null,
                command.getName(),
                command.getDescription(),
                true,
                LocalDateTime.now(),
                null
        );

        Category savedCategory = categoryRepository.save(category);
        
        eventPublisher.publish(CategoryCreatedEvent.of(savedCategory.getId(), savedCategory.getName()));
        
        return savedCategory;
    }
}
