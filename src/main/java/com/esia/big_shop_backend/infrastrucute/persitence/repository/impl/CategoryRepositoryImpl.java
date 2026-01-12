package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.Category;
import com.esia.big_shop_backend.domain.repository.CategoryRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.CategoryId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.CategoryJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.CategoryMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository jpaRepository;
    private final CategoryMapper mapper;

    @Override
    public Category save(Category category) {
        CategoryJpaEntity entity = mapper.toJpaEntity(category);
        CategoryJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return jpaRepository.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(CategoryId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(CategoryId id) {
        return jpaRepository.existsById(id.getValue());
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }
}
