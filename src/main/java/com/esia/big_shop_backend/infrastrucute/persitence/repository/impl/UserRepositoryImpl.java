package com.esia.big_shop_backend.infrastrucute.persitence.repository.impl;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.RoleJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.entity.UserJpaEntity;
import com.esia.big_shop_backend.infrastrucute.persitence.mapper.UserMapper;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.RoleJpaRepository;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toJpaEntity(user);

        // Map roles from domain roleIds to JPA entities
        if (user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
            Set<RoleJpaEntity> roleEntities = new HashSet<>();
            for (RoleId roleId : user.getRoleIds()) {
                roleJpaRepository.findById(roleId.getValue())
                        .ifPresent(roleEntities::add);
            }
            entity.setRoles(roleEntities);
        }

        UserJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        return jpaRepository.findByUsername(username.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return jpaRepository.findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UserId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(UserId id) {
        return jpaRepository.existsById(id.getValue());
    }

    @Override
    public boolean existsByUsername(Username username) {
        return jpaRepository.existsByUsername(username.getValue());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.getValue());
    }
}