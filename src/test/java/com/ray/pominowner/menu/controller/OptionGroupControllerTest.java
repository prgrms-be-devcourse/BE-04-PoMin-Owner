package com.ray.pominowner.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.menu.controller.dto.OptionGroupRequest;
import com.ray.pominowner.menu.controller.dto.OptionGroupWithOptionResponse;
import com.ray.pominowner.menu.controller.dto.OptionUpdateRequest;
import com.ray.pominowner.menu.domain.Option;
import com.ray.pominowner.menu.domain.OptionGroup;
import com.ray.pominowner.menu.service.OptionGroupService;
import com.ray.pominowner.menu.service.vo.OptionGroupUpdateInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        // when, then
        mvc.perform(get("/api/v1/option-groups")
                        .param("optionGroupId", "1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 그룹 등록에 성공한다")
    void successRegisterOption() throws Exception {
        // given
        OptionGroupRequest optionGroupRequest = new OptionGroupRequest("옵션 그룹 1", 10, false, 1L);
        given(optionGroupService.registerOptionGroup(any(OptionGroup.class))).willReturn(1L);

        // when, then
        mvc.perform(post("/api/v1/option-groups")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(optionGroupRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "/api/v1/option-groups/1"));
    }

    @Test
    @WithMockUser
    @DisplayName("옵션 그룹 수정에 성공한다")
    void successUpdateOption() throws Exception {
        // given
        OptionUpdateRequest optionUpdateRequest = new OptionUpdateRequest("옵션 1", 500, true, 1L);
        doNothing().when(optionGroupService).updateOptionGroup(any(OptionGroupUpdateInfo.class));

        // when, then
        mvc.perform(put("/api/v1/option-groups")
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
        doNothing().when(optionGroupService).deleteOptionGroup(any(Long.class));

        // when, then
        mvc.perform(delete("/api/v1/option-groups")
                        .param("optionGroupId", "1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

}
