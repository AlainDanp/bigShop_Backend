package com.esia.big_shop_backend.application.usecase.auth;

import com.esia.big_shop_backend.application.port.output.PasswordEncoderPort;
import com.esia.big_shop_backend.application.port.output.TokenPort;
import com.esia.big_shop_backend.application.usecase.auth.command.RegisterUserCommand;
import com.esia.big_shop_backend.application.usecase.auth.result.RegisterResult;
import com.esia.big_shop_backend.domain.entity.Role;
import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.RoleRepository;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.Password;
import com.esia.big_shop_backend.domain.valueobject.PersonalInfo;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.enums.RoleEnum;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenProvider;

    public RegisterResult execute(RegisterUserCommand command) {
        if (userRepository.existsByEmail(new Email(command.getEmail()))) {
            throw new RuntimeException("Email already in use");
        }

        Role userRole = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        
        Set<RoleId> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());

        String usernameStr = command.getEmail().split("@")[0];
        // Ensure username meets requirements (length, chars)
        if (usernameStr.length() < 3) {
            usernameStr = usernameStr + "user";
        }
        usernameStr = usernameStr.replaceAll("[^a-zA-Z0-9_-]", "");

        User user = new User(
                null, // ID will be generated
                Username.of(usernameStr),
                new Email(command.getEmail()),
                Password.fromPlainText(command.getPassword(), new org.springframework.security.crypto.password.PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return passwordEncoder.encode(rawPassword.toString());
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return passwordEncoder.matches(rawPassword.toString(), encodedPassword);
                    }
                }),
                new PersonalInfo(command.getFirstName(), command.getLastName(), null, null),
                roleIds,
                roles,
                true, // Active by default for now
                false, // Email not verified
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);

        String token = tokenProvider.generateToken(savedUser.getId(), savedUser.getUsername().getValue());

        return RegisterResult.builder()
                .accessToken(token)
                .user(savedUser)
                .message("User registered successfully")
                .build();
    }
}
