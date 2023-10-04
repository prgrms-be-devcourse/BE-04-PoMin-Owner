package com.ray.pominowner.menu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.menu.service.MenuImageService;
import com.ray.pominowner.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
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

        Map<String, Object> request = Map.of("name", "메뉴이름", "info", "메뉴정보", "price", 10000, "storeId", 1);
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
        mvc.perform(multipart("/api/v1/menus")
                        .file(menuRequest)
                        .file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/menus/1"))
                .andDo(
                        document("menu/save",
                                requestParts(
                                        partWithName("request").description("메뉴 등록 요청"),
                                        partWithName("image").description("메뉴 이미지")
                                ),
                                requestPartFields("request",
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("메뉴 이름"),
                                        fieldWithPath("info").type(JsonFieldType.STRING).description("메뉴 정보"),
                                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("메뉴 가격"),
                                        fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("가게 아이디")),
                                responseHeaders(
                                        headerWithName("Location").description("생성 후 세부 정보 redirection link")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("메뉴 업데이트에 성공한다")
    void successUpdateMenu() throws Exception {
        // given
        doNothing().when(menuService).updateMenu(any(), any());
        Long menuId = 1L;

        // when, then
        MockMultipartHttpServletRequestBuilder builder = multipart("/api/v1/menus/{menuId}", menuId);
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
                .andExpect(status().isOk())
                .andDo(
                        document("menu/update",
                                requestParts(
                                        partWithName("request").description("메뉴 등록 요청"),
                                        partWithName("image").description("메뉴 이미지")
                                ),
                                requestPartFields("request",
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("메뉴 이름"),
                                        fieldWithPath("info").type(JsonFieldType.STRING).description("메뉴 정보"),
                                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("메뉴 가격"),
                                        fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("가게 아이디")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("메뉴 업데이트에 성공한다")
    void successAddOptionGroupToMenu() throws Exception {
        // given
        doNothing().when(menuService).addOptionGroupToMenu(any(), any());
        Long menuId = 1L;
        Long optionGroupId = 1L;

        // when, then
        MockMultipartHttpServletRequestBuilder builder = multipart(
                "/api/v1/menus/{menuId}/option-groups/{optionGroupId}",
                menuId,
                optionGroupId
        );

        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mvc.perform(builder
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(
                        document("menu/add-option-group",
                                pathParameters(
                                        parameterWithName("menuId").description("메뉴 아이디"),
                                        parameterWithName("optionGroupId").description("옵션 그룹 아이디")
                                )
                        )
                );
    }

}
