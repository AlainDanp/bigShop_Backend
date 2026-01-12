package com.esia.big_shop_backend.application.usecase.address.command;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressCommand {
    private String userEmail;

    private String fullName;
    private String phone;
    private String street;
    private String city;
    private String zipCode;
    private String country;

    private boolean makeDefault;
}
