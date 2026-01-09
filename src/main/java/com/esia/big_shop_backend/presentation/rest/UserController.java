package com.esia.big_shop_backend.presentation.rest;


import com.esia.big_shop_backend.application.usecase.user.AssignRoleToUserUseCase;
import com.esia.big_shop_backend.application.usecase.user.GetAllUsersUseCase;
import com.esia.big_shop_backend.presentation.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AssignRoleToUserUseCase assignRoleToUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;


    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        // Implementation to get user profile
        return ResponseEntity.ok(new UserResponse());
    }


}
