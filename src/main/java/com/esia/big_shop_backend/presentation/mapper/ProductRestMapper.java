package com.esia.big_shop_backend.presentation.mapper;


import com.esia.big_shop_backend.application.usecase.product.CreateProductCommand;
import com.esia.big_shop_backend.application.usecase.product.UpdateProductCommand;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.presentation.dto.request.product.CreateProductRequest;
import com.esia.big_shop_backend.presentation.dto.request.product.UpdateProductRequest;
import com.esia.big_shop_backend.presentation.dto.response.product.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductRestMapper {

    public CreateProductCommand toCommand(CreateProductRequest request) {
        return new CreateProductCommand(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getDiscountPrice(),
                request.getStockQuantity(),
                request.getCategoryId()
        );
    }

    public UpdateProductCommand toUpdateCommand(Long productId, UpdateProductRequest request) {
        return new UpdateProductCommand(
                productId,
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getDiscountPrice(),
                request.getStockQuantity(),
                request.getCategoryId()
        );
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId() != null ? product.getId().getValue() : null,
                product.getName(),
                product.getDescription(),
                product.getPrice().getAmount(),
                product.getDiscountPrice() = null ? product.getDiscountPrice().getAmount() : null,
                product.getStockQuantity(),
                product.getCategoryId().getValue(),
                null, // categoryName - à récupérer si nécessaire
                product.isActive()
        );

    }
}