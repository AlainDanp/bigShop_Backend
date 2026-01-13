package com.esia.big_shop_backend.application.usecase.auth;

import com.esia.big_shop_backend.application.port.output.TokenPort;
import com.esia.big_shop_backend.application.usecase.auth.command.RefreshTokenCommand;
import com.esia.big_shop_backend.application.usecase.auth.result.AuthToken;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final UserRepository userRepository;
    private final TokenPort tokenProvider;

    public AuthToken execute(RefreshTokenCommand command) {
        if (!tokenProvider.validateToken(command.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        UserId userId = tokenProvider.getUserIdFromToken(command.getRefreshToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newToken = tokenProvider.generateToken(user.getId(), user.getUsername().getValue());

        return AuthToken.builder()
                .accessToken(newToken)
                .user(user)
                .build();
    }
}
