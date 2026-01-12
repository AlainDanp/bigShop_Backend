package com.esia.big_shop_backend.presentation.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAddressRequest {
    @NotBlank private String fullName;
    @NotBlank private String phone;
    @NotBlank private String street;
    @NotBlank private String city;
    @NotBlank private String zipCode;
    @NotBlank private String country;

    private boolean makeDefault;
}
