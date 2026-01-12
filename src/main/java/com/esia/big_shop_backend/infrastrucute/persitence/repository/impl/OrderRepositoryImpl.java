package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.Order;
import com.esia.big_shop_backend.domain.repository.OrderRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.OrderId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.OrderJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.OrderMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = mapper.toJpaEntity(order);
        OrderJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return jpaRepository.findByOrderNumber(orderNumber)
                .map(mapper::toDomain);
    }


    @Override
    public List<Order> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(0, 20);
        return jpaRepository.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByUserId(UserId userId, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return jpaRepository.findByUserId(userId.getValue(), pageable)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(OrderId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(OrderId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
