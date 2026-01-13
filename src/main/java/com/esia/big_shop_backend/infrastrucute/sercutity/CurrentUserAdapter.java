package com.esia.big_shop_backend.infrastrucute.sercutity;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.infrastrucute.persitence.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserAdapter implements CurrentUserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Long getUserIdByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email))
                .getId();
    }

    @Override
    public Long getUserIdByName(String name) {
        return userJpaRepository.findByUsername(name)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + name))
                .getId();
    }
}
