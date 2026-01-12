package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductsByCategoryUseCase {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> execute(Long categoryId, int page, int size) {
        return productRepository.findByCategory(CategoryId.of(categoryId), page, size);
    }
}
