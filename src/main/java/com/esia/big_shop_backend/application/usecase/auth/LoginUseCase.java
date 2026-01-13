package com.esia.big_shop_backend.application.usecase.auth;

import com.esia.big_shop_backend.application.port.output.PasswordEncoderPort;
import com.esia.big_shop_backend.application.port.output.TokenPort;
import com.esia.big_shop_backend.application.usecase.auth.command.LoginCommand;
import com.esia.big_shop_backend.application.usecase.auth.result.AuthToken;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenProvider;

    public AuthToken execute(LoginCommand command) {
        User user = userRepository.findByEmail(new Email(command.getEmail()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword().getValue())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = tokenProvider.generateToken(user.getId(), user.getUsername().getValue());

        return AuthToken.builder()
                .accessToken(token)
                .user(user)
                .build();
    }
}
