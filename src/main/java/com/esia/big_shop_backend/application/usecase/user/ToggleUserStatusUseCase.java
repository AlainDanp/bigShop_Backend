package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ToggleUserStatusUseCase {
    private final UserRepository userRepository;

    @Transactional
    public User execute(Long userId, boolean activate) {
        User user = userRepository.findById(UserId.of(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (activate) {
            user.activate();
        } else {
            user.deactivate();
        }

        return userRepository.save(user);
    }
}
