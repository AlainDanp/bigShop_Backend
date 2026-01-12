package com.esia.big_shop_backend.domain.valueobject;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
public class Password {
    private final String value;

    public static Password fromPlainText(String plainText, PasswordEncoder encoder) {
        String hashed = encoder.encode(plainText);
        return new Password(hashed);
    }
    public boolean matches(String plainText, PasswordEncoder encoder) {
        return encoder.matches(plainText, this.value);
    }
}
