package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAvatarUseCase {
    private final UserRepository userRepository;

    @Transactional
    public User execute(Long userId, String avatarUrl) {
        User user = userRepository.findById(UserId.of(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (avatarUrl == null || avatarUrl.isBlank()) {
            throw new IllegalArgumentException("Avatar URL cannot be empty");
        }

        user.updateAvatar(avatarUrl);

        return userRepository.save(user);
    }
}
