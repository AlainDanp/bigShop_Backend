package com.esia.big_shop_backend.application.usecase.address.query;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressQuery {
    private String userEmail;
    private Long addressId;
}
