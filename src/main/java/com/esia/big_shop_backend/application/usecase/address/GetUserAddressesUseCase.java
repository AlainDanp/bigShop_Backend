package com.esia.big_shop_backend.application.usecase.address;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetUserAddressesUseCase {

    private final AddressRepository addressRepository;
    private final CurrentUserPort currentUserPort;

    public List<Address> execute(String userEmail) {
        Long userId = currentUserPort.getUserIdByEmail(userEmail);
        return addressRepository.findAllByUserId(userId);
    }
}
