package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.application.usecase.user.command.DeleteUserAccountCommand;
import com.esia.big_shop_backend.application.usecase.user.command.UpdateUserProfileCommand;
import com.esia.big_shop_backend.domain.entity.Role;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.presentation.dto.request.user.DeleteUserAccountRequest;
import com.esia.big_shop_backend.presentation.dto.request.user.UpdateUserProfileRequest;
import com.esia.big_shop_backend.presentation.dto.response.user.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserRestMapper {

    public UpdateUserProfileCommand toUpdateCommand(Long userId, UpdateUserProfileRequest request) {
        return new UpdateUserProfileCommand(
                userId,
                request.getFirstName(),
                request.getLastName()
        );
    }

    public DeleteUserAccountCommand toDeleteCommand(Long userId, DeleteUserAccountRequest request) {
        return new DeleteUserAccountCommand(userId);
    }

    public UserResponse toResponse(User user) {
        Set<String> roleNames = user.getRoles() != null
                ? user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
                : Set.of();

        return new UserResponse(
                user.getId() != null ? user.getId().getValue() : null,
                user.getPersonalInfo() != null ? user.getPersonalInfo().getFirstName() : null,
                user.getPersonalInfo() != null ? user.getPersonalInfo().getLastName() : null,
                user.getEmail() != null ? user.getEmail().getValue() : null,
                user.getPersonalInfo() != null ? user.getPersonalInfo().getAvatar() : null,
                user.isActive(),
                roleNames
        );
    }
}
