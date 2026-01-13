package com.esia.big_shop_backend.application.usecase.auth;

import com.esia.big_shop_backend.application.usecase.auth.command.LogoutCommand;
import com.esia.big_shop_backend.application.usecase.auth.result.MessageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    public MessageResult execute(LogoutCommand command) {
        // In a stateless JWT architecture, logout is typically handled on the client side by removing the token.
        // Optionally, we could blacklist the token here if a blacklist mechanism was implemented.
        return new MessageResult("Logged out successfully");
    }
}
