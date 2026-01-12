package com.esia.big_shop_backend.application.usecase.address.command;

public record SetDefaultAddressCommand(
        Long addressId,
        String userEmail
) {}
