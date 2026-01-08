package com.esia.big_shop_backend.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PhoneNumber {
    private final String value;

    private PhoneNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        // Basic validation for international format
        String cleaned = value.replaceAll("[\\s()-]", "");
        if (!cleaned.matches("^\\+?[0-9]{8,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format: " + value);
        }
        this.value = cleaned;
    }

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }
}
