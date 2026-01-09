package com.esia.big_shop_backend.application.port.output;

import com.esia.big_shop_backend.domain.valueobject.ids.UserId;

public interface TokenPort {
    String generateToken(UserId userId, String username);
    boolean validateToken(String token);
    UserId getUserIdFromToken(String token);
    String getUsernameFromToken(String token);
}
