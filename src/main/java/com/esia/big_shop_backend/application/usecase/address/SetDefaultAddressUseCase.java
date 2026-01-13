package com.esia.big_shop_backend.application.usecase.address;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.usecase.address.command.SetDefaultAddressCommand;
import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetDefaultAddressUseCase {

    private final AddressRepository addressRepository;
    private final CurrentUserPort currentUserPort;

    public Address execute(SetDefaultAddressCommand command) {
        Long userId = currentUserPort.getUserIdByEmail(command.userEmail());

        Address address = addressRepository.findByIdAndUserId(command.addressId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        addressRepository.unsetDefaultForUser(userId);
        address.setDefault(true);

        return addressRepository.save(address);
    }

    public Object execute(String name, Long id) {
        Long userId = currentUserPort.getUserIdByName(name);

        Address address = addressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        addressRepository.unsetDefaultForUser(userId);
        address.setDefault(true);
        return addressRepository.save(address);
    }
}
