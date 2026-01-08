package com.esia.big_shop_backend.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

 @Getter
 @AllArgsConstructor
public class PasswordResetToken {
    private final String token;
    private final Date expiryDate;

    boolean isExpired() {
        return new Date().after(expiryDate);
    }
    boolean isValid() {
        return !isExpired();
    }
}
