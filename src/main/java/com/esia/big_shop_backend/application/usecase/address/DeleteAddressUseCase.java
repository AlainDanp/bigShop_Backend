package com.esia.big_shop_backend.application.usecase.address;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import com.esia.big_shop_backend.application.usecase.address.command.DeleteAddressCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAddressUseCase {

    private final AddressRepository addressRepository;
    private final CurrentUserPort currentUserPort;

    public void execute(DeleteAddressCommand command) {
        Long userId = currentUserPort.getUserIdByEmail(command.userEmail());

        Address address = addressRepository.findById(command.addressId()).orElseThrow(() -> new IllegalArgumentException("Address not found: " + command.addressId()));

        if (address.getUserId() == null || !address.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You cannot delete this address.");
        }

        addressRepository.deleteById(command.addressId());
    }

    public void execute(String name, Long id) {
        Long userId = currentUserPort.getUserIdByName(name);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + id));

        if (address.getUserId() == null || !address.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You cannot delete this address.");
        }

        addressRepository.deleteById(id);
    }
}
