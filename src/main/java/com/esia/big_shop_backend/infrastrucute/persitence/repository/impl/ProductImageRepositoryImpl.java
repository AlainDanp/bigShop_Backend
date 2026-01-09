package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.ProductImage;
import com.esia.big_shop_backend.domain.repository.ProductImageRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductImageId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.ProductImageJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.ProductImageMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.ProductImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductImageRepositoryImpl implements ProductImageRepository {

    private final ProductImageJpaRepository jpaRepository;
    private final ProductImageMapper mapper;

    @Override
    public ProductImage save(ProductImage productImage) {
        ProductImageJpaEntity entity = mapper.toJpaEntity(productImage);
        ProductImageJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ProductImage> findById(ProductImageId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<ProductImage> findByProductId(ProductId productId) {
        return jpaRepository.findByProductId(productId.getValue()).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(ProductImageId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(ProductImageId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
