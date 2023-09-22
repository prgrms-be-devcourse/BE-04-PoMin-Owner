package com.ray.pominowner.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.store.controller.dto.CategoryRequest;
import com.ray.pominowner.store.controller.dto.PhoneNumberRequest;
import com.ray.pominowner.store.controller.dto.StoreInformationRequest;
import com.ray.pominowner.store.controller.dto.StoreRegisterRequest;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private StoreService storeService;

    @Test
    @WithMockUser
    @DisplayName("가게 등록에 성공한다")
    void successRegisterStore() throws Exception {
        // given
        StoreRegisterRequest storeRegisterRequest = new StoreRegisterRequest("1234567890", "가게이름", "서울특별시 가게동 주소구", "100동 108동","가게이미지URL.png");
        given(storeService.registerStore(any(Store.class)))
                .willReturn(1L);

        // when, then
        mvc.perform(post("/api/v1/stores")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(storeRegisterRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/stores/1"));
    }

    @Test
    @WithMockUser
    @DisplayName("카테고리 등록에 성공한다")
    void successRegisterCategory() throws Exception {
        // given
        CategoryRequest categoryRequest = new CategoryRequest(List.of("한식", "도시락"));

        // when, then
        mvc.perform(post("/api/v1/stores/1/categories")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("전화번호 등록에 성공한다")
    void successRegisterPhoneNumber() throws Exception {
        // given
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest("01012345678");

        // when, then
        mvc.perform(patch("/api/v1/stores/1/phone-numbers")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(phoneNumberRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("전화번호 삭제에 성공한다")
    void successRemovePhoneNumber() throws Exception {
        // given
        doNothing().when(storeService).deletePhoneNumber(any(Long.class));

        // when, then
        mvc.perform(delete("/api/v1/stores/1/phone-numbers")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("가게 정보 등록에 성공한다")
    void successRegisterInformation() throws Exception {
        // given
        final String input = "가게 정보입니다. 테스트 용도입니다.";
        StoreInformationRequest informationRequest = new StoreInformationRequest(input);

        // when, then
        mvc.perform(patch("/api/v1/stores/1/info")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(informationRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("가게 정보 삭제에 성공한다")
    void successRemoveInformation() throws Exception {
        // given
        doNothing().when(storeService).deleteInformation(any(Long.class));

        // when, then
        mvc.perform(delete("/api/v1/stores/1/info")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("가게 이미지 저장에 성공한다")
    void successSaveImages() throws Exception {
        // given
        MockMultipartFile firstMultipartFile = new MockMultipartFile("TEST", "test1.png", MediaType.IMAGE_PNG_VALUE, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        MockMultipartFile secondMultipartFile = new MockMultipartFile("TEST2", "test2.png", MediaType.IMAGE_PNG_VALUE, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

        // when, then
        mvc.perform(multipart("/api/v1/stores/1/store-images")
                        .file(firstMultipartFile)
                        .file(secondMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}
