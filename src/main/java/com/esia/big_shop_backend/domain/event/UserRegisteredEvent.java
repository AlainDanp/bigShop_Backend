package com.esia.big_shop_backend.domain.event;

import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegisteredEvent {
    private Long userId;
    private String email;
    private LocalDateTime occurredOn;

    public static UserRegisteredEvent of(UserId userId, String email) {
        return new UserRegisteredEvent(userId.getValue(), email, LocalDateTime.now());
    }
}
