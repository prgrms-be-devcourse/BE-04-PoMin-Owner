package com.ray.pominowner.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validator {

    public static void validate(boolean failureCondition, ExceptionMessage exception) {
        if (failureCondition) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

}
