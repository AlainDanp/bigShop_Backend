package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.product.*;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.presentation.dto.response.product.ProductResponse;
import com.esia.big_shop_backend.presentation.dto.request.product.UpdateProductRequest;
import com.esia.big_shop_backend.presentation.dto.request.product.CreateProductRequest;


import com.esia.big_shop_backend.presentation.mapper.ProductRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final ProductRestMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid CreateProductRequest request) {

        CreateProductCommand command = mapper.toCommand(request);
        Product product = createProductUseCase.execute(command);
        ProductResponse response = mapper.toResponse(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Product product = getProductUseCase.execute(id);
        return ResponseEntity.ok(mapper.toResponse(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable) {
        Page<Product> products = getAllProductsUseCase.execute(pageable);
        Page<ProductResponse> response = products.map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductRequest request) {

        UpdateProductCommand command = mapper.toUpdateCommand(id, request);
        Product product = updateProductUseCase.execute(command);
        return ResponseEntity.ok(mapper.toResponse(product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
