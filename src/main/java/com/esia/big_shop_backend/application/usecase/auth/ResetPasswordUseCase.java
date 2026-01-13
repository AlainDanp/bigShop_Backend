package com.esia.big_shop_backend.application.usecase.auth;

import com.esia.big_shop_backend.application.port.output.PasswordEncoderPort;
import com.esia.big_shop_backend.application.port.output.TokenPort;
import com.esia.big_shop_backend.application.usecase.auth.command.ResetPasswordCommand;
import com.esia.big_shop_backend.application.usecase.auth.result.MessageResult;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.Password;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordUseCase {

    private final UserRepository userRepository;
    private final TokenPort tokenProvider;
    private final PasswordEncoderPort passwordEncoder;

    public MessageResult execute(ResetPasswordCommand command) {
        if (!tokenProvider.validateToken(command.getToken())) {
            throw new RuntimeException("Invalid or expired token");
        }

        UserId userId = tokenProvider.getUserIdFromToken(command.getToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(Password.fromPlainText(command.getNewPassword(), new org.springframework.security.crypto.password.PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return passwordEncoder.encode(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return passwordEncoder.matches(rawPassword.toString(), encodedPassword);
            }
        }));

        userRepository.save(user);

        return new MessageResult("Password reset successfully");
    }
}
