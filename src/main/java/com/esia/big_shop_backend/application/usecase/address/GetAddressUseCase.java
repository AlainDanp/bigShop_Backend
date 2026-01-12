package com.esia.big_shop_backend.application.usecase.address;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.usecase.address.query.GetAddressQuery;
import com.esia.big_shop_backend.domain.entity.Address;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAddressUseCase {

    private final AddressRepository addressRepository;
    private final CurrentUserPort currentUserPort;

    public Address execute(GetAddressQuery query) {
        Long userId = currentUserPort.getUserIdByEmail(query.getUserEmail());
        return addressRepository.findByIdAndUserId(query.getAddressId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
    }
}
