package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId productId);
    List<Product> findAll(int page, int size);
    List<Product> findByCategory(CategoryId categoryId, int page, int size);
    List<Product> findActiveProducts(int page, int size);
    List<Product> findProductsOnSale(int page, int size);
    List<Product> findNewProducts(int page, int size);
    List<Product> searchProducts(String keyword, int page, int size);
    void deleteById(ProductId id);
    boolean existsById(ProductId id);
}
