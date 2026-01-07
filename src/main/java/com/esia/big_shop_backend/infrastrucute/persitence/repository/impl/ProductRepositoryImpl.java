package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.Product;
import com.esia.big_shop_backend.domain.repository.ProductRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.ProductId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.ProductJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.ProductMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    private final ProductMapper mapper;

    @Override
    public Product save(Product product) {
        ProductJpaEntity entity = mapper.toJpaEntity(product);
        ProductJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Product> findByCategory(CategoryId categoryId, Pageable pageable) {
        return jpaRepository.findByCategoryId(categoryId.getValue(), pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Product> findActiveProducts(Pageable pageable) {
        return jpaRepository.findByIsActive(true, pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Product> findProductsOnSale(Pageable pageable) {
        return jpaRepository.findProductsOnSale(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(ProductId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(ProductId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
