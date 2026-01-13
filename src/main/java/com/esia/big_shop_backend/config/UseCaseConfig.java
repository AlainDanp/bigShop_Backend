package com.esia.big_shop_backend.config;

import com.esia.big_shop_backend.application.port.output.CurrentUserPort;
import com.esia.big_shop_backend.application.port.output.MtnPaymentPort;

import com.esia.big_shop_backend.application.port.output.OrangeMoneyPaymentPort;
import com.esia.big_shop_backend.application.port.output.StripePaymentPort;
import com.esia.big_shop_backend.application.usecase.address.*;
import com.esia.big_shop_backend.application.usecase.payment.*;
import com.esia.big_shop_backend.domain.repository.AddressRepository;
import com.esia.big_shop_backend.domain.repository.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateAddressUseCase createAddressUseCase(AddressRepository repo, CurrentUserPort userPort) {
        return new CreateAddressUseCase(repo, userPort);
    }

    @Bean
    public UpdateAddressUseCase updateAddressUseCase(AddressRepository repo, CurrentUserPort userPort) {
        return new UpdateAddressUseCase(repo, userPort);
    }

    @Bean
    public DeleteAddressUseCase deleteAddressUseCase(AddressRepository repo, CurrentUserPort userPort) {
        return new DeleteAddressUseCase(repo, userPort);
    }

    @Bean
    public GetAddressUseCase getAddressUseCase(AddressRepository repo, CurrentUserPort userPort) {
        return new GetAddressUseCase(repo, userPort);
    }

    @Bean
    public GetUserAddressesUseCase getUserAddressesUseCase(AddressRepository repo, CurrentUserPort userPort) {
        return new GetUserAddressesUseCase(repo, userPort);
    }

    @Bean
    public SetDefaultAddressUseCase setDefaultAddressUseCase(AddressRepository repo, CurrentUserPort userPort) {
        return new SetDefaultAddressUseCase(repo, userPort);
    }
}
