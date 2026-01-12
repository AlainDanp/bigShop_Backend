package com.esia.big_shop_backend.application.port.output;

public interface CurrentUserPort {
    Long getUserIdByEmail(String email);
}
