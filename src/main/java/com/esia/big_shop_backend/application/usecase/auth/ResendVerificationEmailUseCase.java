package com.esia.big_shop_backend.application.usecase.auth;

import com.esia.big_shop_backend.application.port.output.EmailPort;
import com.esia.big_shop_backend.application.port.output.TokenPort;
import com.esia.big_shop_backend.application.usecase.auth.command.ResendVerificationEmailCommand;
import com.esia.big_shop_backend.application.usecase.auth.result.MessageResult;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResendVerificationEmailUseCase {

    private final UserRepository userRepository;
    private final TokenPort tokenProvider;
    private final EmailPort emailPort;

    public MessageResult execute(ResendVerificationEmailCommand command) {
        User user = userRepository.findByEmail(new Email(command.getEmail()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEmailVerified()) {
            return new MessageResult("Email is already verified");
        }

        String token = tokenProvider.generateToken(user.getId(), user.getUsername().getValue());
        
        // In a real app, this URL should be configurable
        String verificationLink = "http://localhost:4200/verify-email?token=" + token;

        emailPort.sendVerificationEmail(user.getEmail().getValue(), verificationLink);

        return new MessageResult("Verification email sent");
    }
}
