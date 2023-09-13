package com.ray.pominowner.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.controller.dto.ReceiveOrderRequest;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.service.OrderService;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private Order order;

    @BeforeEach
    void setup() {
        order = Order.builder()
                .id(1L)
                .orderNumber("orderNumber")
                .receiptNumber(1)
                .requestedDetails("안맵게 해주세요")
                .totalPrice(30000)
                .customerPhoneNumber(new PhoneNumber("01012345678"))
                .storeId(1L)
                .build();
    }

    @Test
    @WithMockUser
    @DisplayName("주문 접수를 성공한다")
    void successReceiveOrder() throws Exception {
        // given
        ReceiveOrderRequest request = new ReceiveOrderRequest(
                "orderNumber",
                "안맵게 해주세요",
                30000,
                "01012345678",
                null,
                1L
        );

        given(orderService.receiveOrder(any(Order.class))).willReturn(order);

        // when, then
        this.mockMvc.perform(post("/api/v1/orders/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("주문 수락을 성공한다")
    void successApproveOrder() throws Exception {
        given(orderService.approve(any(Long.class))).willReturn(order);

        this.mockMvc.perform(post("/api/v1/orders/1/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("주문 거절을 성공한다")
    void rejectApproveOrder() throws Exception {
        given(orderService.reject(any(Long.class))).willReturn(order);

        this.mockMvc.perform(post("/api/v1/orders/1/reject")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderId", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
