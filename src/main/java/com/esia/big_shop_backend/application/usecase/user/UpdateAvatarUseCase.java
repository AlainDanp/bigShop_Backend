package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.application.usecase.user.command.UpdateAvatarCommand;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.service.UserDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAvatarUseCase {
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    @Transactional
    public User execute(UpdateAvatarCommand command) {
        User user = userRepository.findById(UserId.of(command.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + command.getUserId()));

        if (command.getAvatarUrl() == null || command.getAvatarUrl().isBlank()) {
            throw new IllegalArgumentException("Avatar URL cannot be empty");
        }

        userDomainService.updateAvatar(user, command.getAvatarUrl());

        return userRepository.save(user);
    }
}
