package com.esia.big_shop_backend.infrastrucute.sercutity;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.repository.UserRepository;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(Username.of(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new CustomUserDetails(
                user.getId().getValue(),
                user.getUsername().getValue(),
                user.getEmail().getValue(),
                user.getPassword().getValue(),
                user.isActive(),
                true,
                true,
                user.isActive(),
                getAuthorities(user)
        );
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(UserId.of(id))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return new CustomUserDetails(
                user.getId().getValue(),
                user.getUsername().getValue(),
                user.getEmail().getValue(),
                user.getPassword().getValue(),
                user.isActive(),
                true,
                true,
                user.isActive(),
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> {
                    String roleName = role.getName().name(); // Convert Enum to String
                    if (!roleName.startsWith("ROLE_")) {
                        roleName = "ROLE_" + roleName;
                    }
                    return new SimpleGrantedAuthority(roleName);
                })
                .collect(Collectors.toList());
    }
}
