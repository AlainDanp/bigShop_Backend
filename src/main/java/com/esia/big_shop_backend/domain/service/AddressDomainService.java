package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressDomainService {

    public void updateAddressDetails(Address address, String fullName, String phone, String street, String city, String zipCode, String country) {
        address.setFullName(fullName);
        address.setPhone(phone);
        address.setStreet(street);
        address.setCity(city);
        address.setZipCode(zipCode);
        address.setCountry(country);
    }

    public void setDefault(Address address) {
        address.setDefault(true);
    }

    public void unsetDefault(Address address) {
        address.setDefault(false);
    }
}
