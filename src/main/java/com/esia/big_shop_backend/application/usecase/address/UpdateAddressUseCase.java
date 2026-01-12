package com.esia.big_shop_backend.application.usecase.address;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.usecase.address.command.UpdateAddressCommand;
import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateAddressUseCase {

    private final AddressRepository addressRepository;
    private final CurrentUserPort currentUserPort;

    public Address execute(UpdateAddressCommand cmd) {
        Long userId = currentUserPort.getUserIdByEmail(cmd.getUserEmail());

        Address existing = addressRepository.findByIdAndUserId(cmd.getAddressId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        existing.setFullName(cmd.getFullName());
        existing.setPhone(cmd.getPhone());
        existing.setStreet(cmd.getStreet());
        existing.setCity(cmd.getCity());
        existing.setZipCode(cmd.getZipCode());
        existing.setCountry(cmd.getCountry());

        return addressRepository.save(existing);
    }
}
