package com.esia.big_shop_backend.domain.service;

import com.esia.big_shop_backend.domain.entity.User;
import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.PersonalInfo;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class UserDomainService {

    public void updateProfile(User user, String firstName, String lastName, String avatar) {
        PersonalInfo currentInfo = user.getPersonalInfo();
        user.setPersonalInfo(new PersonalInfo(
                firstName != null ? firstName : currentInfo.getFirstName(),
                lastName != null ? lastName : currentInfo.getLastName(),
                currentInfo.getDateOfBirth(),
                avatar != null ? avatar : currentInfo.getAvatar()
        ));
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void changeEmail(User user, Email newEmail) {
        if (newEmail == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (!user.getEmail().equals(newEmail)) {
            user.setEmail(newEmail);
            user.setEmailVerified(false); // Require re-verification
            user.setUpdatedAt(LocalDateTime.now());
        }
    }

    public void changeUsername(User user, Username newUsername) {
        if (newUsername == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        user.setUsername(newUsername);
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void changePassword(User user, String newHashedPassword) {
        if (newHashedPassword == null || newHashedPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        user.setPassword(newHashedPassword);
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void updateAvatar(User user, String avatarUrl) {
        PersonalInfo currentInfo = user.getPersonalInfo();
        user.setPersonalInfo(new PersonalInfo(
                currentInfo.getFirstName(),
                currentInfo.getLastName(),
                currentInfo.getDateOfBirth(),
                avatarUrl
        ));
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void activate(User user) {
        user.setActive(true);
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void deactivate(User user) {
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void unverifyEmail(User user) {
        user.setEmailVerified(false);
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void addRole(User user, RoleId roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID cannot be null");
        }
        if (user.getRoleIds() == null) {
            user.setRoleIds(new HashSet<>());
        }
        user.getRoleIds().add(roleId);
        user.setUpdatedAt(LocalDateTime.now());
    }

    public void removeRole(User user, RoleId roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("Role ID cannot be null");
        }
        if (user.getRoleIds() != null) {
            user.getRoleIds().remove(roleId);
            user.setUpdatedAt(LocalDateTime.now());
        }
    }

    public boolean hasRole(User user, RoleId roleId) {
        return user.getRoleIds() != null && user.getRoleIds().contains(roleId);
    }

    public boolean canLogin(User user) {
        return user.isActive() && user.isEmailVerified();
    }

    public boolean isAccountActive(User user) {
        return user.isActive();
    }
}
