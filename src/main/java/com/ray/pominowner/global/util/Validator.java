package com.ray.pominowner.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validator {

    public static void validate(boolean condition, ExceptionMessage exception) {
        if (condition) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

}
