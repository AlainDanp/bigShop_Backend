package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.product.*;
import com.esia.big_shop_backend.application.usecase.product.command.CreateProductCommand;
import com.esia.big_shop_backend.application.usecase.product.command.DeleteProductCommand;
import com.esia.big_shop_backend.application.usecase.product.command.UpdateProductCommand;
import com.esia.big_shop_backend.application.usecase.product.query.GetAllProductsQuery;
import com.esia.big_shop_backend.application.usecase.product.query.GetProductQuery;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.presentation.dto.request.product.CreateProductRequest;
import com.esia.big_shop_backend.presentation.dto.request.product.UpdateProductRequest;
import com.esia.big_shop_backend.presentation.dto.response.product.ProductResponse;
import com.esia.big_shop_backend.presentation.mapper.ProductRestMapper;
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
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final ProductRestMapper mapper;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid CreateProductRequest request) {

        CreateProductCommand command = mapper.toCommand(request);
        Product product = createProductUseCase.execute(command);
        ProductResponse response = mapper.toResponse(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all products with pagination")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        GetAllProductsQuery query = new GetAllProductsQuery(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().toString(),
                "ASC"
        );
        
        Object result = getAllProductsUseCase.execute(query);
        if (result instanceof Page) {
             Page<Product> products = (Page<Product>) result;
             return ResponseEntity.ok(products.map(mapper::toResponse));
        } else {
             List<Product> products = (List<Product>) result;
             List<ProductResponse> responses = products.stream().map(mapper::toResponse).collect(Collectors.toList());
             return ResponseEntity.ok(new PageImpl<>(responses, pageable, responses.size()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductRequest request) {

        UpdateProductCommand command = mapper.toUpdateCommand(id, request);
        Product product = updateProductUseCase.execute(command);
        return ResponseEntity.ok(mapper.toResponse(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        DeleteProductCommand command = new DeleteProductCommand(id);
        deleteProductUseCase.execute(command.getProductId());
        return ResponseEntity.noContent().build();
    }
}
