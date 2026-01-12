package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.user.*;
import com.esia.big_shop_backend.application.usecase.user.command.ChangePasswordCommand;
import com.esia.big_shop_backend.application.usecase.user.command.UpdateUserProfileCommand;
import com.esia.big_shop_backend.application.usecase.user.query.GetAllUsersQuery;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.presentation.dto.request.user.ChangePasswordRequest;
import com.esia.big_shop_backend.presentation.dto.request.user.UpdateUserProfileRequest;
import com.esia.big_shop_backend.presentation.dto.response.user.UserResponse;
import com.esia.big_shop_backend.presentation.mapper.UserRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final ToggleUserStatusUseCase toggleUserStatusUseCase;
    private final DeleteUserAccountUseCase deleteUserAccountUseCase;
    private final UserRestMapper mapper;

    @GetMapping("/profile")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<UserResponse> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        User user = getUserProfileUseCase.execute(userId);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @PutMapping("/profile")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<UserResponse> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid UpdateUserProfileRequest request) {
        Long userId = getUserIdFromUserDetails(userDetails);
        UpdateUserProfileCommand command = mapper.toUpdateCommand(userId, request);
        User user = updateUserProfileUseCase.execute(command);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid ChangePasswordRequest request) {
        Long userId = getUserIdFromUserDetails(userDetails);
        ChangePasswordCommand command = new ChangePasswordCommand(userId, request.getCurrentPassword(), request.getNewPassword());
        changePasswordUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users (Admin)")
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        GetAllUsersQuery query = new GetAllUsersQuery(pageable.getPageNumber(), pageable.getPageSize());
        
        Object result = getAllUsersUseCase.execute(query);
        if (result instanceof Page) {
             Page<User> users = (Page<User>) result;
             return ResponseEntity.ok(users.map(mapper::toResponse));
        } else {
             List<User> users = (List<User>) result;
             List<UserResponse> responses = users.stream().map(mapper::toResponse).collect(Collectors.toList());
             return ResponseEntity.ok(new PageImpl<>(responses, pageable, responses.size()));
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Toggle user status (Admin)")
    public ResponseEntity<Void> toggleUserStatus(@PathVariable Long id) {
        toggleUserStatusUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user account (Admin)")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
        deleteUserAccountUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        // Placeholder: Implement actual user ID extraction logic
        throw new UnsupportedOperationException("User ID extraction not implemented");
    }
}
