package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchProductsUseCase {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> execute(String keyword, int page , int size) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }
        return productRepository.searchProducts(keyword, page, size);
    }
}
