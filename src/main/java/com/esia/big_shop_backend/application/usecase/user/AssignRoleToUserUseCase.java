package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.application.usecase.user.command.AssignRoleCommand;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.service.UserDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignRoleToUserUseCase {
    private final UserRepository userRepository;
    private final UserDomainService userDomainService;

    @Transactional
    public User execute(AssignRoleCommand command) {
        User user = userRepository.findById(UserId.of(command.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + command.getUserId()));

        userDomainService.addRole(user, RoleId.of(command.getRoleId()));

        return userRepository.save(user);
    }
}
