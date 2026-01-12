package com.esia.big_shop_backend.domain.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Long id;
    private Long userId;

    private String fullName;
    private String phone;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    private boolean isDefault;
}
