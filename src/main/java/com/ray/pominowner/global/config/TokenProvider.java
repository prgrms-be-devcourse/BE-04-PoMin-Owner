package com.ray.pominowner.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TokenProvider {

    private final String businessNumberServiceKey;

    private final String businessNumberHeaderAuthKey;

    public TokenProvider(@Value("${api.token.business.query:''}") String businessNumberServiceKey,
                         @Value("${api.token.business.header:''}") String businessNumberHeaderAuthKey) {
        this.businessNumberServiceKey = businessNumberServiceKey;
        this.businessNumberHeaderAuthKey = businessNumberHeaderAuthKey;
    }

}
