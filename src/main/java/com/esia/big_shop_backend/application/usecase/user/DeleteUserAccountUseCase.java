package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.application.port.output.EventPublisher;
import com.esia.big_shop_backend.domain.event.UserDeletedEvent;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserAccountUseCase {
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public void execute(Long userId) {
        UserId id = UserId.of(userId);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        userRepository.deleteById(id);
        
        eventPublisher.publish(UserDeletedEvent.of(id));
    }
}
