package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.auth.*;
import com.esia.big_shop_backend.application.usecase.auth.command.*;
import com.esia.big_shop_backend.application.usecase.auth.result.AuthToken;
import com.esia.big_shop_backend.application.usecase.auth.result.MessageResult;
import com.esia.big_shop_backend.application.usecase.auth.result.RegisterResult;
import com.esia.big_shop_backend.presentation.dto.request.auth.ForgotPasswordRequest;
import com.esia.big_shop_backend.presentation.dto.request.auth.LoginRequest;
import com.esia.big_shop_backend.presentation.dto.request.auth.RegisterRequest;
import com.esia.big_shop_backend.presentation.dto.request.auth.ResetPasswordRequest;
import com.esia.big_shop_backend.presentation.dto.response.auth.AuthResponse;
import com.esia.big_shop_backend.presentation.dto.response.auth.MessageResponse;
import com.esia.big_shop_backend.presentation.mapper.AuthRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and authorization APIs")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final ResendVerificationEmailUseCase resendVerificationEmailUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final AuthRestMapper mapper;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        RegisterUserCommand command = mapper.toRegisterCommand(request);
        RegisterResult result = registerUserUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toAuthResponse(result.getAccessToken(), result.getUser()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginCommand command = mapper.toLoginCommand(request);
        AuthToken result = loginUseCase.execute(command);
        return ResponseEntity.ok(mapper.toAuthResponse(result.getAccessToken(), result.getUser()));
    }
}
