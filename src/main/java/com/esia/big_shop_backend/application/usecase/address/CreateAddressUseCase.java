package com.esia.big_shop_backend.application.usecase.address;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.usecase.address.command.CreateAddressCommand;
import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAddressUseCase {

    private final AddressRepository addressRepository;
    private final CurrentUserPort currentUserPort;

    public Address execute(CreateAddressCommand cmd) {
        Long userId = currentUserPort.getUserIdByEmail(cmd.getUserEmail());

        if (cmd.isMakeDefault()) {
            addressRepository.unsetDefaultForUser(userId);
        }

        Address toSave = Address.builder()
                .userId(userId)
                .fullName(cmd.getFullName())
                .phone(cmd.getPhone())
                .street(cmd.getStreet())
                .city(cmd.getCity())
                .zipCode(cmd.getZipCode())
                .country(cmd.getCountry())
                .isDefault(cmd.isMakeDefault())
                .build();

        return addressRepository.save(toSave);
    }
}
