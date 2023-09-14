package com.ray.pominowner.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode(of = "tel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNumber {

    private static final String DEFAULT_VALUE = "NOT_SELECTED";
    private static final String REGEX = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";

    @Column(columnDefinition = "TEXT default 'NOT_SELECTED'")
    private String tel = DEFAULT_VALUE;

    public PhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);

        this.tel = phoneNumber;
    }

    private void validatePhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "올바른 전화번호를 입력해 주세요.");
        Assert.isTrue(Pattern.matches(REGEX, phoneNumber), "올바른 전화번호를 입력해 주세요.");
    }

    public String getTel() {
        return tel;
    }

}
