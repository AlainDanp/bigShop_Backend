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

    @PostMapping("/logout")
    @Operation(summary = "Logout current user")
    public ResponseEntity<MessageResponse> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            LogoutCommand command = new LogoutCommand(token);
            logoutUseCase.execute(command);
        }
        return ResponseEntity.ok(mapper.toMessageResponse("Logged out successfully"));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        ForgotPasswordCommand command = mapper.toForgotPasswordCommand(request);
        MessageResult result = forgotPasswordUseCase.execute(command);
        return ResponseEntity.ok(mapper.toMessageResponse(result.getMessage()));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password with token")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        ResetPasswordCommand command = mapper.toResetPasswordCommand(request);
        MessageResult result = resetPasswordUseCase.execute(command);
        return ResponseEntity.ok(mapper.toMessageResponse(result.getMessage()));
    }

    @GetMapping("/verify-email")
    @Operation(summary = "Verify email with token")
    public ResponseEntity<MessageResponse> verifyEmail(@RequestParam String token) {
        VerifyEmailCommand command = mapper.toVerifyEmailCommand(token);
        MessageResult result = verifyEmailUseCase.execute(command);
        return ResponseEntity.ok(mapper.toMessageResponse(result.getMessage()));
    }

    @PostMapping("/resend-verification")
    @Operation(summary = "Resend verification email")
    public ResponseEntity<MessageResponse> resendVerificationEmail(@RequestParam String email) {
        ResendVerificationEmailCommand command = new ResendVerificationEmailCommand(email);
        MessageResult result = resendVerificationEmailUseCase.execute(command);
        return ResponseEntity.ok(mapper.toMessageResponse(result.getMessage()));
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        RefreshTokenCommand command = mapper.toRefreshTokenCommand(refreshToken);
        AuthToken result = refreshTokenUseCase.execute(command);
        return ResponseEntity.ok(mapper.toAuthResponse(result.getAccessToken(), result.getUser()));
    }
}
