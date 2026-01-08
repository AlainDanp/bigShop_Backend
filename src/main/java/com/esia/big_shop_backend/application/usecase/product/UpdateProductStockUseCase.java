package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductStockUseCase {
    private final ProductRepository productRepository;

    @Transactional
    public Product execute(Long productId, int quantity, StockOperation operation) {
        Product product = productRepository.findById(ProductId.of(productId))
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        switch (operation) {
            case INCREASE -> product.increaseStock(quantity);
            case DECREASE -> product.decreaseStock(quantity);
        }

        return productRepository.save(product);
    }

    public enum StockOperation {
        INCREASE, DECREASE
    }
}
