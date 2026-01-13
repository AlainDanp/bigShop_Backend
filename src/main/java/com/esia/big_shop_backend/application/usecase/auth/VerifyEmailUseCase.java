package com.esia.big_shop_backend.application.usecase.auth;

import com.esia.big_shop_backend.application.port.output.TokenPort;
import com.esia.big_shop_backend.application.usecase.auth.command.VerifyEmailCommand;
import com.esia.big_shop_backend.application.usecase.auth.result.MessageResult;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyEmailUseCase {

    private final UserRepository userRepository;
    private final TokenPort tokenProvider;

    public MessageResult execute(VerifyEmailCommand command) {
        if (!tokenProvider.validateToken(command.getToken())) {
            throw new RuntimeException("Invalid or expired token");
        }

        UserId userId = tokenProvider.getUserIdFromToken(command.getToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmailVerified(true);
        userRepository.save(user);

        return new MessageResult("Email verified successfully");
    }
}
