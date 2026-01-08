package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.ProductImage;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductImageId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductImageRepository {
    ProductImage save(ProductImage productImage);
    Optional<ProductImage> findById(ProductImageId id);
    List<ProductImage> findByProductId(ProductId productId);
    void deleteById(ProductImageId id);
    boolean existsById(ProductImageId id);
}
