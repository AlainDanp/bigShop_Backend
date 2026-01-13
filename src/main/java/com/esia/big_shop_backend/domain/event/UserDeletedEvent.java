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
public class UserDeletedEvent {
    private Long userId;
    private LocalDateTime occurredOn;

    public static UserDeletedEvent of(UserId userId) {
        return new UserDeletedEvent(userId.getValue(), LocalDateTime.now());
    }
}
