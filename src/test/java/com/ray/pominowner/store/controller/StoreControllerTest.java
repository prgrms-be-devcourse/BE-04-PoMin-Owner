package com.ray.pominowner.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.store.controller.dto.CategoryRequest;
import com.ray.pominowner.store.controller.dto.StoreRegisterRequest;
import com.ray.pominowner.store.domain.Store;
import com.ray.pominowner.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        StoreRegisterRequest storeRegisterRequest = new StoreRegisterRequest("1234567890", "가게이름", "서울특별시 가게동 주소구", "가게이미지URL.png");
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

}
