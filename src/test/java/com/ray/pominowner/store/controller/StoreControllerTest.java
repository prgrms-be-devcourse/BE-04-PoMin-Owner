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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
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
        StoreRegisterRequest storeRegisterRequest = new StoreRegisterRequest("1234567890", "가게이름", "서울특별시 가게동 주소구", "100동 108동", "가게이미지URL.png");
        given(storeService.registerStore(any(Store.class)))
                .willReturn(1L);
        
        // when, then
        mvc.perform(post("/api/v1/stores")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(storeRegisterRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/stores/1"))
                .andDo(
                        document("save/store",
                                requestFields(
                                        fieldWithPath("businessNumber").type(JsonFieldType.STRING).description("사업자 등록번호"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("가게 이름"),
                                        fieldWithPath("mainAddress").type(JsonFieldType.STRING).description("가게 메인 주소"),
                                        fieldWithPath("detailAddress").type(JsonFieldType.STRING).description("가게 상세 주소"),
                                        fieldWithPath("logoImage").type(JsonFieldType.STRING).description("가게 로고 이미지 URL")
                                ),
                                responseHeaders(
                                        headerWithName("Location").description("생성 후 세부 정보 redirection link")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("카테고리 등록에 성공한다")
    void successRegisterCategory() throws Exception {
        // given
        CategoryRequest categoryRequest = new CategoryRequest(List.of("한식", "도시락"));
        Long storeId = 1L;

        // when, then
        mvc.perform(post("/api/v1/stores/{storeId}/categories", storeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andDo(
                        document("save/category",
                                pathParameters(
                                        parameterWithName("storeId").description("가게 id")
                                ),
                                requestFields(
                                        fieldWithPath("categories").type(JsonFieldType.ARRAY).description("카테고리")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("전화번호 등록에 성공한다")
    void successRegisterPhoneNumber() throws Exception {
        // given
        PhoneNumberRequest phoneNumberRequest = new PhoneNumberRequest("01012345678");
        Long storeId = 1L;

        // when, then
        mvc.perform(patch("/api/v1/stores/{storeId}/phone-numbers", storeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(phoneNumberRequest)))
                .andExpect(status().isOk())
                .andDo(
                        document("update/phone-number",
                               pathParameters(
                                        parameterWithName("storeId").description("가게 id")
                               ),
                               requestFields(
                                        fieldWithPath("phoneNumber").type(JsonFieldType.STRING).description("전화번호")
                               )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("전화번호 삭제에 성공한다")
    void successRemovePhoneNumber() throws Exception {
        // given
        doNothing().when(storeService).deletePhoneNumber(any(Long.class));
        Long storeId = 1L;

        // when, then
        mvc.perform(delete("/api/v1/stores/{storeId}/phone-numbers", storeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("delete/phone-number",
                                pathParameters(
                                        parameterWithName("storeId").description("가게 id")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("가게 정보 등록에 성공한다")
    void successRegisterInformation() throws Exception {
        // given
        final String input = "가게 정보입니다. 테스트 용도입니다.";
        StoreInformationRequest informationRequest = new StoreInformationRequest(input);
        Long storeId = 1L;

        // when, then
        mvc.perform(patch("/api/v1/stores/{storeId}/info", storeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(informationRequest)))
                .andExpect(status().isOk())
                .andDo(
                        document("update/storeInfo",
                                pathParameters(
                                        parameterWithName("storeId").description("가게 id")
                                ),
                                requestFields(
                                        fieldWithPath("information").type(JsonFieldType.STRING).description("가게 정보")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("가게 정보 삭제에 성공한다")
    void successRemoveInformation() throws Exception {
        // given
        doNothing().when(storeService).deleteInformation(any(Long.class));
        Long storeId = 1L;

        // when, then
        mvc.perform(delete("/api/v1/stores/{storeId}/info", storeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("delete/storeInfo",
                                pathParameters(
                                        parameterWithName("storeId").description("가게 id")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("가게 이미지 저장에 성공한다")
    void successSaveImages() throws Exception {
        // given
        MockMultipartFile firstMultipartFile = new MockMultipartFile("image1", "test1.png", MediaType.IMAGE_PNG_VALUE, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        MockMultipartFile secondMultipartFile = new MockMultipartFile("image2", "test2.png", MediaType.IMAGE_PNG_VALUE, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        MockMultipartFile thirdMultipartFile = new MockMultipartFile("image3", "test3.png", MediaType.IMAGE_PNG_VALUE, UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        Long storeId = 1L;

        // when, then
        mvc.perform(multipart("/api/v1/stores/{storeId}/store-images", storeId)
                        .file(firstMultipartFile)
                        .file(secondMultipartFile)
                        .file(thirdMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(
                        document("delete/storeInfo",
                                pathParameters(
                                        parameterWithName("storeId").description("가게 id")
                                ),
                                requestParts(
                                        partWithName("image1").description("파일1"),
                                        partWithName("image2").description("파일2"),
                                        partWithName("image3").description("파일3")
                                )
                        )
                );

    }

}
