package com.ray.pominowner.settlement.controller;

import com.ray.pominowner.settlement.controller.dto.DailySettlementResponse;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.service.SettlementService;
import com.ray.pominowner.settlement.service.vo.SettlementByStoreRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ray.pominowner.settlement.SettlementTestFixture.settlement;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(SettlementController.class)
class SettlementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SettlementService settlementService;

    @MockBean
    private DailySettlementResponseConverter converter;


    @Test
    @WithMockUser
    @DisplayName("주문 정보에 따른 정산 조회에 성공한다.")
    public void successGetSettlementByOrder() throws Exception {
        // given
        given(settlementService.getSettlementByOrder(settlement().getId())).willReturn(settlement());

        // when
        ResultActions actions = this.mockMvc.perform(get("/api/v1/settlements/by-order/{orderId}", settlement().getOrderId())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("/api/v1/settlements/by-order/{orderId}",
                                pathParameters(
                                        parameterWithName("orderId").description("주문 id")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("정산 id"),
                                        fieldWithPath("salesAmount").type(JsonFieldType.NUMBER).description("판매 금액"),
                                        fieldWithPath("paymentFee").type(JsonFieldType.NUMBER).description("결제 수수료"),
                                        fieldWithPath("brokerageFee").type(JsonFieldType.NUMBER).description("중개 수수료"),
                                        fieldWithPath("valueAddedFee").type(JsonFieldType.NUMBER).description("부가세"),
                                        fieldWithPath("payOutAmount").type(JsonFieldType.NUMBER).description("지불 금액"),
                                        fieldWithPath("payOutDate").type(JsonFieldType.STRING).description("지불 날짜")
                                )
                        )
                );
    }

    @Test
    @WithMockUser
    @DisplayName("일 정산 조회에 성공한다.")
    public void successGetDailySettlementByStore() throws Exception {
        // given
        DailySettlementResponse response = new DailySettlementResponse(settlement());
        List<Settlement> settlements = List.of(settlement());
        given(settlementService.getDailySettlementByStore(any(SettlementByStoreRequest.class))).willReturn(settlements);

        List<DailySettlementResponse> expectedResponses = List.of(response);
        given(converter.convert(settlements)).willReturn(expectedResponses);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = currentDate.format(formatter);

        // when
        ResultActions actions = this.mockMvc.perform(get("/api/v1/settlements/by-store/{storeId}", settlement().getStoreId())
                .param("dateType", "SALES")
                .param("startDate", date)
                .param("endDate", date)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("/api/v1/settlements/by-store/{storeId}",
                                pathParameters(
                                        parameterWithName("storeId").description("가게 id")
                                ),
                                queryParameters(
                                        parameterWithName("dateType").description("조회 기준 날짜 타입 (SALES/PAYOUT)"),
                                        parameterWithName("startDate").description("시작 날짜 (예: '2023-09-21')"),
                                        parameterWithName("endDate").description("종료 날짜 (예: '2023-09-21')")
                                ),
                                responseFields(
                                        fieldWithPath("[]").type(JsonFieldType.ARRAY).description("정산 배열"),
                                        fieldWithPath("[].payOutDate").type(JsonFieldType.STRING).description("지불 날짜"),
                                        fieldWithPath("[].depositStatus").type(JsonFieldType.STRING).description("입금 상태"),
                                        fieldWithPath("[].salesDate").type(JsonFieldType.STRING).description("판매 날짜"),
                                        fieldWithPath("[].serviceType").type(JsonFieldType.STRING).description("이용 서비스 타입"),
                                        fieldWithPath("[].salesAmount").type(JsonFieldType.NUMBER).description("판매 금액"),
                                        fieldWithPath("[].paymentFee").type(JsonFieldType.NUMBER).description("결제 수수료"),
                                        fieldWithPath("[].brokerageFee").type(JsonFieldType.NUMBER).description("중개 수수료"),
                                        fieldWithPath("[].valueAddedFee").type(JsonFieldType.NUMBER).description("부가세"),
                                        fieldWithPath("[].payOutAmount").type(JsonFieldType.NUMBER).description("지불 금액")
                                )

                        )
                );
    }

}
