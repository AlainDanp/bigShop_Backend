package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.Role;
import com.esia.big_shop_backend.domain.repository.RoleRepository;
import com.esia.big_shop_backend.domain.valueobject.enums.RoleEnum;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.RoleJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.RoleMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository jpaRepository;
    private final RoleMapper mapper;

    @Override
    public Role save(Role role) {
        RoleJpaEntity entity = mapper.toJpaEntity(role);
        RoleJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(RoleEnum name) {
        RoleJpaEntity.RoleName jpaName = mapToJpaRoleName(name);
        return jpaRepository.findByName(jpaName)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(RoleId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(RoleId id) {
        return jpaRepository.existsById(id.getValue());
    }

    private RoleJpaEntity.RoleName mapToJpaRoleName(RoleEnum roleEnum) {
        if (roleEnum == null) return null;
        return switch (roleEnum) {
            case ADMIN -> RoleJpaEntity.RoleName.ADMIN;
            case USER -> RoleJpaEntity.RoleName.USER;
            case MODERATOR -> RoleJpaEntity.RoleName.MODERATOR;
        };
    }
}
