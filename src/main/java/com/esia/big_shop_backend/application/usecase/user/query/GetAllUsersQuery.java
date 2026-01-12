package com.esia.big_shop_backend.application.usecase.user.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersQuery {
    private int page = 0;
    private int size = 20;
}
