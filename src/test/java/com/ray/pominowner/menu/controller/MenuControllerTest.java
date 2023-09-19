package com.ray.pominowner.menu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.menu.service.MenuImageService;
import com.ray.pominowner.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private MenuService menuService;

    @MockBean
    private MenuImageService menuImageService;

    private MockMultipartFile image;

    private MockMultipartFile menuRequest;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        image = new MockMultipartFile(
                "image",
                "image.png",
                MediaType.IMAGE_PNG_VALUE,
                UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

        Map<String, String> request = Map.of("name", "메뉴이름", "info", "메뉴정보", "price", "10000");
        menuRequest = new MockMultipartFile(
                "request",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                mapper.writeValueAsString(request)
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    @Test
    @WithMockUser
    @DisplayName("메뉴 등록에 성공한다")
    void successRegisterMenu() throws Exception {
        // given
        given(menuService.registerMenu(any())).willReturn(1L);

        // when, then
        mvc.perform(multipart("/api/v1/menus/stores/1")
                        .file(menuRequest)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/menus/1/stores/1"));
    }

    @Test
    @WithMockUser
    @DisplayName("메뉴 업데이트에 성공한다")
    void successUpdateMenu() throws Exception {
        // given
        doNothing().when(menuService).updateMenu(any(), any());


        // when, then
        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/v1/menus/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mvc.perform(builder
                        .file(menuRequest)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
