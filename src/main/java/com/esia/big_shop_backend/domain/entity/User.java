package com.esia.big_shop_backend.domain.entity;

import com.esia.big_shop_backend.domain.valueobject.Email;
import com.esia.big_shop_backend.domain.valueobject.PersonalInfo;
import com.esia.big_shop_backend.domain.valueobject.Username;
import com.esia.big_shop_backend.domain.valueobject.ids.RoleId;
import com.esia.big_shop_backend.domain.valueobject.ids.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class User {
    private final UserId id;
    private Username username;
    private Email email;
    private String password; // hashed password
    private PersonalInfo personalInfo;
    private Set<RoleId> roleIds;
    private boolean active;
    private boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Business logic methods
    public void updateProfile(String firstName, String lastName, String avatar) {
        this.personalInfo = new PersonalInfo(
                firstName != null ? firstName : personalInfo.getFirstName(),
                lastName != null ? lastName : personalInfo.getLastName(),
                personalInfo.getDateOfBirth(),
                avatar != null ? avatar : personalInfo.getAvatar()
        );
        this.updatedAt = LocalDateTime.now();
    }

    public void changeEmail(Email newEmail) {
        if (!this.email.equals(newEmail)) {
            this.email = newEmail;
            this.emailVerified = false; // Require re-verification
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void changePassword(String newHashedPassword) {
        if (newHashedPassword == null || newHashedPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = newHashedPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateAvatar(String avatarUrl) {
        this.personalInfo = new PersonalInfo(
                personalInfo.getFirstName(),
                personalInfo.getLastName(),
                personalInfo.getDateOfBirth(),
                avatarUrl
        );
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void verifyEmail() {
        this.emailVerified = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void addRole(RoleId roleId) {
        if (this.roleIds == null) {
            this.roleIds = new HashSet<>();
        }
        this.roleIds.add(roleId);
        this.updatedAt = LocalDateTime.now();
    }

    public void removeRole(RoleId roleId) {
        if (this.roleIds != null) {
            this.roleIds.remove(roleId);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public boolean hasRole(RoleId roleId) {
        return roleIds != null && roleIds.contains(roleId);
    }
}
