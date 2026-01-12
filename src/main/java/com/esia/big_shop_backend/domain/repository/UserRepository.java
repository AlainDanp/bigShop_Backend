package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByUsername(Username username);
    Optional<User> findByEmail(Email email);
    List<User> findAll(int page, int size);
    void deleteById(UserId id);
    boolean existsById(UserId id);
    boolean existsByUsername(Username username);
    boolean existsByEmail(Email email);
}
