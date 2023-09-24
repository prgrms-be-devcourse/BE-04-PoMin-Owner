package com.ray.pominowner.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.menu.controller.dto.OptionRequest;
import com.ray.pominowner.menu.controller.dto.OptionResponse;
import com.ray.pominowner.menu.controller.dto.OptionUpdateRequest;
import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.service.OptionGroupService;
import com.ray.pominowner.menu.service.OptionService;
import com.ray.pominowner.menu.service.vo.OptionUpdateInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(OptionController.class)
class OptionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OptionService optionService;

    @MockBean
    private OptionGroupService optionGroupService;

    private OptionGroup optionGroup;

    private Option option;

    @BeforeEach
    void setUp() {
        optionGroup = OptionGroup.builder()
                .id(1L)
                .name("옵션 그룹 1")
                .maxOptionCount(10)
                .required(true)
                .storeId(1L)
                .build();

        option = Option.builder()
                .id(1L)
                .name("옵션 1")
                .price(500)
                .selected(true)
                .optionGroup(optionGroup)
                .build();
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 조회에 성공한다")
    void successGetOption() throws Exception {
        // given
        given(optionService.getOption(any(Long.class))).willReturn(option);
        OptionResponse response = OptionResponse.from(option);
        Long optionId = 1L;

        // when, then
        mvc.perform(get("/api/v1/options/{optionId}", optionId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andDo(
                        document("option/find",
                                pathParameters(
                                        parameterWithName("optionId").description("옵션 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("name").type(STRING).description("옵션 이름"),
                                        fieldWithPath("price").type(NUMBER).description("옵션 가격"),
                                        fieldWithPath("selected").type(BOOLEAN).description("옵션 선택 여부")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 등록에 성공한다")
    void successRegisterOption() throws Exception {
        // given
        OptionRequest optionRequest = new OptionRequest("옵션 1", 500, true, 1L);
        given(optionGroupService.getOptionGroup(any(Long.class))).willReturn(optionGroup);
        given(optionService.registerOption(any(Option.class), any(OptionGroup.class))).willReturn(1L);

        // when, then
        mvc.perform(post("/api/v1/options")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(optionRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/options/1"));
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 수정에 성공한다")
    void successUpdateOption() throws Exception {
        // given
        OptionUpdateRequest optionUpdateRequest = new OptionUpdateRequest("옵션 1", 500, true);
        Long optionId = 1L;
        doNothing().when(optionService).updateOption(any(OptionUpdateInfo.class), any(Long.class));

        // when, then
        mvc.perform(put("/api/v1/options/{optionId}", optionId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(optionUpdateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 삭제에 성공한다")
    void successDeleteOption() throws Exception {
        // given
        doNothing().when(optionService).deleteOption(any(Long.class));
        Long optionId = 1L;

        // when, then
        mvc.perform(delete("/api/v1/options/{optionId}", optionId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(
                        document("option/delete",
                                pathParameters(
                                        parameterWithName("optionId").description("옵션 아이디")
                                )
                        )
                );
    }

}
