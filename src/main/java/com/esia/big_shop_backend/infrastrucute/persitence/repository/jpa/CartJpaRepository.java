package com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa;

import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartJpaRepository extends JpaRepository<CartJpaEntity, Long> {
    Optional<CartJpaEntity> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
