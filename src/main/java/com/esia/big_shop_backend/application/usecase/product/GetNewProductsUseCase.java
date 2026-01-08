package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetNewProductsUseCase {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> execute(Pageable pageable) {
        return productRepository.findNewProducts(pageable);
    }
}
