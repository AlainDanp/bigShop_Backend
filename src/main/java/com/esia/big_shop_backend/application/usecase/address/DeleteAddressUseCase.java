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
