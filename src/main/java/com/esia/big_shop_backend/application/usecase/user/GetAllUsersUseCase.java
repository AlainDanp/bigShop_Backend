package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> execute(int page) {
        return userRepository.findAll(0, Integer.MAX_VALUE);
    }
}
