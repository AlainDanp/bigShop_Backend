package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.application.usecase.category.command.CreateCategoryCommand;
import com.esia.big_shop_backend.application.usecase.category.command.UpdateCategoryCommand;
import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.presentation.dto.request.category.CreateCategoryRequest;
import com.esia.big_shop_backend.presentation.dto.request.category.UpdateCategoryRequest;
import com.esia.big_shop_backend.presentation.dto.response.category.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryRestMapper {

    public CreateCategoryCommand toCommand(CreateCategoryRequest request) {
        return new CreateCategoryCommand(
                request.getName(),
                request.getParentId(),
                request.getDescription()
        );
    }

    public UpdateCategoryCommand toUpdateCommand(Long categoryId, UpdateCategoryRequest request) {
        return new UpdateCategoryCommand(
                categoryId,
                request.getName(),
                request.getDescription()
        );
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId() != null ? category.getCategoryId().getValue() : null,
                category.getName(),
                category.getDescription(),
                category.getParentId() != null ? category.getParentId(): null,
                category.isActive()
        );
    }
}
