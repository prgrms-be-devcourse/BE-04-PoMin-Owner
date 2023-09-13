package com.ray.pominowner.global.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("yml에 설정된 올바른 토큰을 가져올 수 있다.")
    void successGetCorrectToken() {
        String businessNumberServiceKey = tokenProvider.getBusinessNumberServiceKey();
        String businessNumberHeaderAuthKey = tokenProvider.getBusinessNumberHeaderAuthKey();
        log.info("businessNumberServiceKey: {}, businessNumberHeaderAuthKey: {}", businessNumberServiceKey, businessNumberHeaderAuthKey);

        assertThat(businessNumberServiceKey).isNotBlank();
        assertThat(businessNumberHeaderAuthKey).isNotBlank();
    }

}
