package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.application.usecase.user.command.DeleteUserAccountCommand;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserAccountUseCase {
    private final UserRepository userRepository;

    @Transactional
    public void execute(DeleteUserAccountCommand command) {
        UserId id = UserId.of(command.getUserId());
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + command.getUserId());
        }
        userRepository.deleteById(id);
    }
}
