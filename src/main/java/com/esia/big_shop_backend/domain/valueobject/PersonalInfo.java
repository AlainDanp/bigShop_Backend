package com.esia.big_shop_backend.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PersonalInfo {
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
    private final String avatar;
}
