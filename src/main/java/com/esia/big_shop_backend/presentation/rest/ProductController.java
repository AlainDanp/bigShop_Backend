package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.product.*;
import com.esia.big_shop_backend.application.usecase.product.command.CreateProductCommand;
import com.esia.big_shop_backend.application.usecase.product.command.DeleteProductCommand;
import com.esia.big_shop_backend.application.usecase.product.command.UpdateProductCommand;
import com.esia.big_shop_backend.application.usecase.product.query.GetActiveProductsQuery;
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
    private final GetProductsByCategoryUseCase getProductsByCategoryUseCase;
    private final SearchProductsUseCase searchProductsUseCase;
    private final GetActiveProductsUseCase getActiveProductsUseCase;
    private final GetProductsOnSaleUseCase getProductsOnSaleUseCase;
    private final GetNewProductsUseCase getNewProductsUseCase;
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

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        GetProductQuery query = new GetProductQuery(id);
        Product product = getProductUseCase.execute(query.getProductId());
        return ResponseEntity.ok(mapper.toResponse(product));
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

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<Product> products = getProductsByCategoryUseCase.execute(categoryId, page, size);
        return ResponseEntity.ok(products.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by keyword")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<Product> products = searchProductsUseCase.execute(keyword, page, size);
        return ResponseEntity.ok(products.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active products")
    public ResponseEntity<List<ProductResponse>> getActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        GetActiveProductsQuery query = new GetActiveProductsQuery(page, size);
        List<Product> products = getActiveProductsUseCase.execute(query);
        return ResponseEntity.ok(products.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/on-sale")
    @Operation(summary = "Get products on sale")
    public ResponseEntity<List<ProductResponse>> getProductsOnSale(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<Product> products = getProductsOnSaleUseCase.execute(page, size);
        return ResponseEntity.ok(products.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/new")
    @Operation(summary = "Get new products")
    public ResponseEntity<List<ProductResponse>> getNewProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<Product> products = getNewProductsUseCase.execute(page, size);
        return ResponseEntity.ok(products.stream().map(mapper::toResponse).collect(Collectors.toList()));
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
