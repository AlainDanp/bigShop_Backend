package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.category.*;
import com.esia.big_shop_backend.application.usecase.category.command.CreateCategoryCommand;
import com.esia.big_shop_backend.application.usecase.category.command.DeleteCategoryCommand;
import com.esia.big_shop_backend.application.usecase.category.command.UpdateCategoryCommand;
import com.esia.big_shop_backend.application.usecase.category.query.GetAllCategoriesQuery;
import com.esia.big_shop_backend.application.usecase.category.query.GetCategoryQuery;
import com.esia.big_shop_backend.application.usecase.category.query.GetRootCategoriesQuery;
import com.esia.big_shop_backend.application.usecase.category.query.GetSubCategoriesQuery;
import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.presentation.dto.request.category.CreateCategoryRequest;
import com.esia.big_shop_backend.presentation.dto.request.category.UpdateCategoryRequest;
import com.esia.big_shop_backend.presentation.dto.response.category.CategoryResponse;
import com.esia.big_shop_backend.presentation.mapper.CategoryRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final GetSubCategoriesUseCase getSubCategoriesUseCase;
    private final GetRootCategoriesUseCase getRootCategoriesUseCase;
    private final CategoryRestMapper mapper;

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        CreateCategoryCommand command = mapper.toCommand(request);
        Category category = createCategoryUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(category));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        GetCategoryQuery query = new GetCategoryQuery(id);
        Category category = getCategoryUseCase.execute(query);
        return ResponseEntity.ok(mapper.toResponse(category));
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<Page<CategoryResponse>> getAllCategories(Pageable pageable) {
        GetAllCategoriesQuery query = new GetAllCategoriesQuery(pageable.getPageNumber(), pageable.getPageSize());
        
        List<Category> result = getAllCategoriesUseCase.execute(query);
        if (result instanceof Page) {
             Page<Category> categories = (Page<Category>) result;
             return ResponseEntity.ok(categories.map(mapper::toResponse));
        } else {
             List<Category> categories = result;
             List<CategoryResponse> responses = categories.stream().map(mapper::toResponse).collect(Collectors.toList());
             return ResponseEntity.ok(new PageImpl<>(responses, pageable, responses.size()));
        }
    }

    @GetMapping("/root")
    @Operation(summary = "Get root categories")
    public ResponseEntity<List<CategoryResponse>> getRootCategories() {
        GetRootCategoriesQuery query = new GetRootCategoriesQuery();
        List<Category> categories = getRootCategoriesUseCase.execute(query);
        return ResponseEntity.ok(
                categories.stream()
                        .map(mapper::toResponse)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}/subcategories")
    @Operation(summary = "Get subcategories")
    public ResponseEntity<List<CategoryResponse>> getSubCategories(@PathVariable Long id) {
        GetSubCategoriesQuery query = new GetSubCategoriesQuery();

        List<Category> categories = getSubCategoriesUseCase.execute(query);

        return ResponseEntity.ok(
                categories.stream()
                        .map(mapper::toResponse)
                        .collect(Collectors.toList())
        );
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a category")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCategoryRequest request) {
        UpdateCategoryCommand command = mapper.toUpdateCommand(id, request);
        Category category = updateCategoryUseCase.execute(command);
        return ResponseEntity.ok(mapper.toResponse(category));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        DeleteCategoryCommand command = new DeleteCategoryCommand(id);
        deleteCategoryUseCase.execute(command);
        return ResponseEntity.noContent().build();
    }
}
