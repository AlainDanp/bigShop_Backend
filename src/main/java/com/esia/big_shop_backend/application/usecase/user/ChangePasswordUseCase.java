package com.esia.big_shop_backend.application.usecase.user;

import com.esia.big_shop_backend.application.port.output.PasswordEncoderPort;
import com.esia.big_shop_backend.application.usecase.user.command.ChangePasswordCommand;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.service.UserDomainService;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final UserDomainService userDomainService;

    @Transactional
    public void execute(ChangePasswordCommand command) {
        User user = userRepository.findById(UserId.of(command.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + command.getUserId()));

        // Verify current password
        if (!passwordEncoder.matches(command.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Validate new password
        if (command.getNewPassword() == null || command.getNewPassword().length() < 8) {
            throw new IllegalArgumentException("New password must be at least 8 characters long");
        }

        // Change password
        String hashedPassword = passwordEncoder.encode(command.getNewPassword());
        userDomainService.changePassword(user, hashedPassword);

        userRepository.save(user);
    }
}
