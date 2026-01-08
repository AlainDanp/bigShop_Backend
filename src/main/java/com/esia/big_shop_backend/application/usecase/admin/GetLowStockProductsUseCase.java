package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetLowStockProductsUseCase {
    private final ProductRepository productRepository;
    private static final int LOW_STOCK_THRESHOLD = 10;

    @Transactional(readOnly = true)
    public List<Product> execute() {
        Page<Product> allProducts = productRepository.findAll(Pageable.unpaged());

        return allProducts.getContent().stream()
                .filter(product -> product.getStockQuantity() <= LOW_STOCK_THRESHOLD)
                .filter(Product::isActive)
                .sorted((a, b) -> Integer.compare(a.getStockQuantity(), b.getStockQuantity()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Product> execute(int threshold) {
        Page<Product> allProducts = productRepository.findAll(Pageable.unpaged());

        return allProducts.getContent().stream()
                .filter(product -> product.getStockQuantity() <= threshold)
                .filter(Product::isActive)
                .sorted((a, b) -> Integer.compare(a.getStockQuantity(), b.getStockQuantity()))
                .collect(Collectors.toList());
    }
}
