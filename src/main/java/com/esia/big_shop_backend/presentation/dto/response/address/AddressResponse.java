package com.esia.big_shop_backend.presentation.dto.response.address;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Long id;

    private String fullName;
    private String phone;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    private boolean isDefault;
}
