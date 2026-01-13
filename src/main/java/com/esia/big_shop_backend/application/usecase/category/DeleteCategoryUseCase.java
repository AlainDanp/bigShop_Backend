package com.esia.big_shop_backend.application.usecase.category;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.application.usecase.category.command.DeleteCategoryCommand;
import com.esia.big_shop_backend.domain.event.CategoryDeletedEvent;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public void execute(DeleteCategoryCommand command) {
        CategoryId categoryId = CategoryId.of(command.getCategoryId());
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category not found with id: " + command.getCategoryId());
        }
        categoryRepository.deleteById(categoryId);
        
        eventPublisher.publish(CategoryDeletedEvent.of(categoryId));
    }
}
