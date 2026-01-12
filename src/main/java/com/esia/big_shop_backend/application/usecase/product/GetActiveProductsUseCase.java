package com.esia.big_shop_backend.application.usecase.product;

import com.esia.big_shop_backend.application.usecase.product.query.GetActiveProductsQuery;
import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetActiveProductsUseCase {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> execute(GetActiveProductsQuery query) {
        return productRepository.findActiveProducts(query.getPage(), query.getSize());
    }
}
