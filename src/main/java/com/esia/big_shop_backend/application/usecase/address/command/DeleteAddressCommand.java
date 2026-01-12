package com.esia.big_shop_backend.application.usecase.address.command;

public record DeleteAddressCommand(
        Long addressId,
        String userEmail
) {}
