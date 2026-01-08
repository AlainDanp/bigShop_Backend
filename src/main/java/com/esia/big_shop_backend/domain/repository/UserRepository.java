package com.esia.big_shop_backend.domain.repository;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByUsername(Username username);
    Optional<User> findByEmail(Email email);
    Page<User> findAll(Pageable pageable);
    void deleteById(UserId id);
    boolean existsById(UserId id);
    boolean existsByUsername(Username username);
    boolean existsByEmail(Email email);
}
