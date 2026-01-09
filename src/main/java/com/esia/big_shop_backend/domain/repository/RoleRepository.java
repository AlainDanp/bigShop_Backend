package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.Role;
import com.esia.big_shop_backend.domain.valueobject.enums.RoleEnum;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findById(RoleId id);
    Optional<Role> findByName(RoleEnum name);
    void deleteById(RoleId id);
    boolean existsById(RoleId id);
}
