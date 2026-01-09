package com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa;

import com.esia.big_shop_backend.infrastrucute.persitence.entity.ProductImageJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageJpaRepository extends JpaRepository<ProductImageJpaEntity, Long> {
    List<ProductImageJpaEntity> findByProductId(Long productId);
    void deleteByProductId(Long productId);
}
