package com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa;


import com.esia.big_shop_backend.infrastrucute.persitence.entity.ProductJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    Page<ProductJpaEntity> findByIsActive(Boolean isActive, Pageable pageable);
    Page<ProductJpaEntity> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT p FROM ProductJpaEntity p WHERE p.isActive = true AND " +
            "(p.discountPrice IS NOT NULL AND p.discountPrice < p.price)")
    Page<ProductJpaEntity> findProductsOnSale(Pageable pageable);
}