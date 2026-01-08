package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetUserProfileUseCase {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User execute(Long userId) {
        return userRepository.findById(UserId.of(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }
}
