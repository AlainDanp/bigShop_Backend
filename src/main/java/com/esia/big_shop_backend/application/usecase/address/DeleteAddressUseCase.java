package com.esia.big_shop_backend.application.usecase.address;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteAddressUseCase {

    private final AddressRepository addressRepository;
    private final CurrentUserPort currentUserPort;

    public void execute(String userEmail, Long addressId) {
        Long userId = currentUserPort.getUserIdByEmail(userEmail);
        addressRepository.deleteByIdAndUserId(addressId, userId);
    }
}
package com.esia.big_shop_backend.application.usecase.address.query;

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

        Address address = addressRepository.findById(command.addressId())
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + command.addressId()));

        // Sécurité : empêcher de supprimer l’adresse d’un autre user
        if (address.getUserId() == null || !address.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You cannot delete this address.");
        }

        addressRepository.deleteById(command.addressId());
    }
}
