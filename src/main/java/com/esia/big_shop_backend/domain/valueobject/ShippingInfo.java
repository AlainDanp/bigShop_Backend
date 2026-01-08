package com.esia.big_shop_backend.domain.valueobject;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShippingInfo {
    private final String fullName;
    private final PhoneNumber phoneNumber;
    private final String addressText;
}
