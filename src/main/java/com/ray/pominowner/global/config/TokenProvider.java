package com.ray.pominowner.global.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:sub/application-token.yml")
public class TokenProvider {

    private final String businessNumberServiceKey;

    public TokenProvider(@Value("${api.token.business:''}") String businessNumberServiceKey) {
        this.businessNumberServiceKey = businessNumberServiceKey;
    }

}
