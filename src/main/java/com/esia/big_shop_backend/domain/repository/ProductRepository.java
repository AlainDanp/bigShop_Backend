package com.esia.big_shop_backend.domain.repository;


import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId id);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByCategory(CategoryId categoryId, Pageable pageable);
    Page<Product> findActiveProducts(Pageable pageable);
    Page<Product> findProductsOnSale(Pageable pageable);
    void deleteById(ProductId id);
    boolean existsById(ProductId id);
}