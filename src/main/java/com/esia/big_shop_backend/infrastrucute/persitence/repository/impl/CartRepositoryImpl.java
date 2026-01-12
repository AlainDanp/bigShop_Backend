package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.Cart;
import com.esia.big_shop_backend.domain.repository.CartRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CartId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CartJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.CartMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.CartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaRepository jpaRepository;
    private final CartMapper mapper;

    @Override
    public Cart save(Cart cart) {
        CartJpaEntity entity = mapper.toJpaEntity(cart);
        CartJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Cart> findById(CartId cartId) {
        return jpaRepository.findById(cartId.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Cart> findByUserId(UserId userId) {
        return jpaRepository.findByUserId(userId.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<Cart> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jpaRepository.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(CartId cartId) {
        jpaRepository.deleteById(cartId.getValue());
    }

    @Override
    public void deleteByUserId(UserId userId) {
        jpaRepository.deleteByUserId(userId.getValue());
    }

    @Override
    public boolean existsById(CartId cartId) {
        return jpaRepository.existsById(cartId.getValue());
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return jpaRepository.existsByUserId(userId.getValue());
    }
}
