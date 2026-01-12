package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.CartItem;
import com.esia.big_shop_backend.domain.repository.CartItemRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CartItemId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartItemJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.CartItemMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.CartItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

    private final CartItemJpaRepository jpaRepository;
    private final CartItemMapper mapper;

    @Override
    public CartItem save(CartItem cartItem) {

        CartItemJpaEntity entity = mapper.toJpaEntity(cartItem, null); 
        CartItemJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<CartItem> findById(CartItemId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(CartItemId id) {
        jpaRepository.deleteById(id.getValue());
    }
}
