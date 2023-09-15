package com.ray.pominowner.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.controller.dto.ReceiveOrderRequest;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.service.OrderService;
import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.domain.PaymentStatus;
import com.ray.pominowner.payment.dto.PaymentCreateRequest;
import com.ray.pominowner.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

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
                .paymentId(1L)
                .orderedAt(LocalDateTime.now())
                .build();
    }


    @Test
    @WithMockUser
    @DisplayName("주문 접수를 성공한다")
    void successReceiveOrder() throws Exception {
        // given
        ReceiveOrderRequest request = new ReceiveOrderRequest(
                1L,
                "orderNumber",
                "안맵게 해주세요",
                30000,
                "01012345678",
                null,
                LocalDateTime.now(),
                1L,
                new PaymentCreateRequest(
                        1L,
                        10000,
                        PaymentStatus.COMPLETE,
                        PGType.TOSS
                )
        );

        Payment payment = Payment.builder()
                .id(1L)
                .amount(10000)
                .status(PaymentStatus.COMPLETE)
                .provider(PGType.TOSS)
                .build();

        given(orderService.receiveOrder(any(Order.class))).willReturn(order);
        given(paymentService.create(any(Payment.class))).willReturn(payment);

        // when, then
        this.mockMvc.perform(post("/api/v1/orders")
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

    @Test
    @WithMockUser
    @DisplayName("오늘의 주문 내역을 가져오기를 성공한다")
    void successGetTodayOrders() throws Exception {
        given(orderService.getTodayOrders(any(Long.class))).willReturn(List.of(order));

        Long storeId = 1L;

        this.mockMvc.perform(get("/api/v1/orders/{storeId}/today", storeId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
