package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.service.UserDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    @Transactional
    public User execute(UpdateUserProfileCommand command) {
        User user = userRepository.findById(UserId.of(command.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + command.getUserId()));
        userDomainService.updateProfile(user, command.getFirstName(), command.getLastName(), null);
        return userRepository.save(user);
    }
}
