package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetProductsByCategoryUseCase {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> execute(Long categoryId, Pageable pageable) {
        return productRepository.findByCategory(CategoryId.of(categoryId), pageable);
    }
}
