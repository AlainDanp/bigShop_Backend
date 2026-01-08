package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignRoleToUserUseCase {
    private final UserRepository userRepository;

    @Transactional
    public User execute(Long userId, Long roleId) {
        User user = userRepository.findById(UserId.of(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.addRole(RoleId.of(roleId));

        return userRepository.save(user);
    }
}
