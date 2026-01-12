package com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa;

import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemJpaRepository extends JpaRepository<CartItemJpaEntity, Long> {
    List<CartItemJpaEntity> findByCartId(Long cartId);
    void deleteByCartId(Long cartId);
}
