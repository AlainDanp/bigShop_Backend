package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.presentation.dto.response.address.AddressResponse;

public class AddressRestMapper {

    public AddressResponse toResponse(Address a) {
        return AddressResponse.builder()
                .id(a.getId())
                .fullName(a.getFullName())
                .phone(a.getPhone())
                .street(a.getStreet())
                .city(a.getCity())
                .zipCode(a.getZipCode())
                .country(a.getCountry())
                .isDefault(a.isDefault())
                .build();
    }
}
