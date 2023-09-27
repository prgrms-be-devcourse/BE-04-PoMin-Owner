package com.ray.pominowner.store.service;

import com.ray.pominowner.store.StoreTestFixture;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class StoreImageServiceTest {

    @Autowired
    private StoreImageService storeImageService;

    @Autowired
    private StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
        storeRepository.save(StoreTestFixture.store());
    }

//    @Test
//    @DisplayName("가게 이미지 등록에 성공한다")
//    void successRegisterStoreImage() {
//        // given
//        MockMultipartFile firstMultipartFile = new MockMultipartFile("TEST", "test1.png", MediaType.IMAGE_PNG_VALUE, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
//        MockMultipartFile secondMultipartFile = new MockMultipartFile("TEST2", "test2.png", MediaType.IMAGE_PNG_VALUE, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
//        List<MultipartFile> images = List.of(firstMultipartFile, secondMultipartFile);
//        Store store = storeRepository.findById(1L).orElseThrow();
//
//        // when, then
//        assertThatNoException()
//                .isThrownBy(() -> storeImageService.saveImages(images, store));
//    }

}
