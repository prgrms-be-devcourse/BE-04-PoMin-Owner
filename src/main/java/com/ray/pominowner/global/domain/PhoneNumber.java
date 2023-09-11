package com.ray.pominowner.global.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {

    private final String phoneRegex = "^(01[016789]){1}([0-9]{3,4}){1}([0-9]{4}){1}$";

    private String value;

    public PhoneNumber(String value) {
        validate(value);

        this.value = value;
    }


    private void validate(String value) {
        if (!Pattern.matches(phoneRegex, value)) {
            throw new IllegalArgumentException("잘못된 전화번호 입니다");
        }
    }

}
