package com.esia.big_shop_backend.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {
    private final String value;

    boolean isValid() {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return value != null && value.matches(emailRegex);
    }
    String getDomain() {
        if (!isValid()) {
            throw new IllegalStateException("Invalid email address");
        }
        return value.substring(value.indexOf("@") + 1);
    }
}
