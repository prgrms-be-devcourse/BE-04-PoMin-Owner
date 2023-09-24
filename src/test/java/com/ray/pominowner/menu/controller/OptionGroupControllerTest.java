package com.ray.pominowner.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.menu.controller.dto.OptionGroupRequest;
import com.ray.pominowner.menu.controller.dto.OptionGroupUpdateRequest;
import com.ray.pominowner.menu.controller.dto.OptionGroupWithOptionResponse;
import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.service.OptionGroupService;
import com.ray.pominowner.menu.service.vo.OptionGroupUpdateInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(OptionGroupController.class)
class OptionGroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OptionGroupService optionGroupService;

    private OptionGroup optionGroup;

    @BeforeEach
    void setUp() {
        optionGroup = OptionGroup.builder()
                .name("옵션 그룹 1")
                .maxOptionCount(10)
                .required(false)
                .storeId(1L)
                .build();

        Option option = Option.builder()
                .id(1L)
                .name("옵션 1")
                .price(500)
                .selected(true)
                .optionGroup(optionGroup)
                .build();

        optionGroup.addOption(option);
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 그룹 조회에 성공한다")
    void successGetOption() throws Exception {
        // given
        OptionGroupWithOptionResponse response = OptionGroupWithOptionResponse.from(optionGroup);
        given(optionGroupService.getOptionGroup(any(Long.class))).willReturn(optionGroup);
        Long optionGroupId = 1L;

        // when, then
        mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/option-groups/{optionGroupId}", optionGroupId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andDo(
                        document("option-group/find",
                                pathParameters(
                                        parameterWithName("optionGroupId").description("옵션 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("optionGroup.name").type(STRING).description("옵션 그룹 이름"),
                                        fieldWithPath("optionGroup.maxOptionCount").type(NUMBER).description("옵션 최대 선택 가능 개수"),
                                        fieldWithPath("optionGroup.required").type(BOOLEAN).description("옵션 필수 선택 여부"),
                                        fieldWithPath("options").type(ARRAY).description("옵션 정보 리스트"),
                                        fieldWithPath("options[].name").type(STRING).description("옵션 이름"),
                                        fieldWithPath("options[].price").type(NUMBER).description("옵션 가격"),
                                        fieldWithPath("options[].selected").type(BOOLEAN).description("옵션 선택 여부")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 그룹 등록에 성공한다")
    void successRegisterOption() throws Exception {
        // given
        Long storeId = 1L;
        Long optionGroupId = 1L;
        OptionGroupRequest optionGroupRequest = new OptionGroupRequest("옵션 그룹 1", 10, false, storeId);
        given(optionGroupService.registerOptionGroup(any(OptionGroup.class))).willReturn(optionGroupId);
        
        // when, then
        mvc.perform(post("/api/v1/option-groups")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(optionGroupRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/option-groups/1"))
                .andDo(
                        document("option-group/save",
                                requestFields(
                                        fieldWithPath("name").type(STRING).description("옵션 그룹 이름"),
                                        fieldWithPath("maxOptionCount").type(NUMBER).description("옵션 최대 선택 가능 개수"),
                                        fieldWithPath("required").type(BOOLEAN).description("옵션 필수 선택 여부"),
                                        fieldWithPath("storeId").type(NUMBER).description("옵션 그룹이 속한 가게 id")
                                )
                ));
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 그룹 수정에 성공한다")
    void successUpdateOption() throws Exception {
        // given
        OptionGroupUpdateRequest optionGroupUpdateRequest = new OptionGroupUpdateRequest("옵션 1", 500, true);
        doNothing().when(optionGroupService).updateOptionGroup(any(OptionGroupUpdateInfo.class), any(Long.class));
        Long optionGroupId = 1L;

        // when, then
        mvc.perform(RestDocumentationRequestBuilders.put("/api/v1/option-groups/{optionGroupId}", optionGroupId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(optionGroupUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(
                        document("option-group/update",
                                pathParameters(
                                        parameterWithName("optionGroupId").description("옵션 그룹 id")
                                ),
                                requestFields(
                                        fieldWithPath("name").type(STRING).description("옵션 그룹 이름"),
                                        fieldWithPath("maxOptionCount").type(NUMBER).description("옵션 최대 선택 가능 개수"),
                                        fieldWithPath("required").type(BOOLEAN).description("옵션 필수 선택 여부")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 삭제에 성공한다")
    void successDeleteOption() throws Exception {
        // given
        doNothing().when(optionGroupService).deleteOptionGroup(any(Long.class));
        Long optionGroupId = 1L;

        // when, then
        mvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/option-groups/{optionGroupId}", optionGroupId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(
                        document("option-group/delete",
                                pathParameters(
                                        parameterWithName("optionGroupId").description("옵션 그룹 id")
                                )
                        )
                );
    }

}

