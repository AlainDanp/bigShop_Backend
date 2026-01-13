package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.user.*;
import com.esia.big_shop_backend.application.usecase.user.command.ChangePasswordCommand;
import com.esia.big_shop_backend.application.usecase.user.command.DeleteUserAccountCommand;
import com.esia.big_shop_backend.application.usecase.user.command.UpdateUserProfileCommand;
import com.esia.big_shop_backend.application.usecase.user.query.GetAllUsersQuery;
import com.esia.big_shop_backend.application.usecase.user.query.GetUserProfileQuery;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.presentation.dto.request.user.ChangePasswordRequest;
import com.esia.big_shop_backend.presentation.dto.request.user.DeleteUserAccountRequest;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final UserRestMapper mapper;

    @GetMapping("/profile")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<UserResponse> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        User user = getUserProfileUseCase.execute(new GetUserProfileQuery(userId));
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


    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof com.esia.big_shop_backend.infrastrucute.sercutity.CustomUserDetails) {
            return ((com.esia.big_shop_backend.infrastrucute.sercutity.CustomUserDetails) userDetails).getUserId();
        }
        throw new IllegalStateException("UserDetails is not an instance of CustomUserDetails");
    }
}
