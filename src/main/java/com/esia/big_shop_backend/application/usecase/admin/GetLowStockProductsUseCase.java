package com.esia.big_shop_backend.application.usecase.admin;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetLowStockProductsUseCase {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> execute(int query) {
        List<Product> allProducts = productRepository.findAll(query.getPage(), query.getSize());

        return allProducts.stream()
                .filter(product -> product.getStockQuantity() <= query.getThreshold())
                .filter(Product::isActive)
                .sorted((a, b) -> Integer.compare(a.getStockQuantity(), b.getStockQuantity()))
                .collect(Collectors.toList());
    }
}
