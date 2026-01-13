package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.application.usecase.auth.command.*;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.presentation.dto.request.auth.ForgotPasswordRequest;
import com.esia.big_shop_backend.presentation.dto.request.auth.LoginRequest;
import com.esia.big_shop_backend.presentation.dto.request.auth.RegisterRequest;
import com.esia.big_shop_backend.presentation.dto.request.auth.ResetPasswordRequest;
import com.esia.big_shop_backend.presentation.dto.response.auth.AuthResponse;
import com.esia.big_shop_backend.presentation.dto.response.auth.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthRestMapper {

    private final UserRestMapper userRestMapper;

    public RegisterUserCommand toRegisterCommand(RegisterRequest request) {
        return new RegisterUserCommand(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        );
    }

    public LoginCommand toLoginCommand(LoginRequest request) {
        return new LoginCommand(
                request.getEmail(),
                request.getPassword()
        );
    }

    public ForgotPasswordCommand toForgotPasswordCommand(ForgotPasswordRequest request) {
        return new ForgotPasswordCommand(request.getEmail());
    }

    public ResetPasswordCommand toResetPasswordCommand(ResetPasswordRequest request) {
        return new ResetPasswordCommand(
                request.getToken(),
                request.getNewPassword()
        );
    }

    public VerifyEmailCommand toVerifyEmailCommand(String token) {
        return new VerifyEmailCommand(token);
    }

    public RefreshTokenCommand toRefreshTokenCommand(String refreshToken) {
        return new RefreshTokenCommand(refreshToken);
    }

    public AuthResponse toAuthResponse(String token, User user) {
        return new AuthResponse(
                token,
                userRestMapper.toResponse(user)
        );
    }

    public MessageResponse toMessageResponse(String message) {
        return new MessageResponse(message);
    }

    public MessageResponse toMessageResponse(String message, String status) {
        return new MessageResponse(message, status);
    }
}
