package com.esia.big_shop_backend.presentation.mapper;

import com.esia.big_shop_backend.application.usecase.address.command.CreateAddressCommand;
import com.esia.big_shop_backend.application.usecase.address.command.UpdateAddressCommand;
import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.presentation.dto.request.address.CreateAddressRequest;
import com.esia.big_shop_backend.presentation.dto.request.address.UpdateAddressRequest;
import com.esia.big_shop_backend.presentation.dto.response.address.AddressResponse;
import org.springframework.stereotype.Component;

@Component
public class AddressRestMapper {

    public CreateAddressCommand toCommand(CreateAddressRequest request, String userEmail) {
        return CreateAddressCommand.builder()
                .userEmail(userEmail)
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .street(request.getStreet())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .makeDefault(request.isMakeDefault())
                .build();
    }

    public UpdateAddressCommand toUpdateCommand(Long addressId, UpdateAddressRequest request, String userEmail) {
        return UpdateAddressCommand.builder()
                .userEmail(userEmail)
                .addressId(addressId)
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .street(request.getStreet())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .build();
    }

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
