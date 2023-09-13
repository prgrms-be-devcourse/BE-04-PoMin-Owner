package com.ray.pominowner.payment.controller;

import com.ray.pominowner.payment.controller.base.ControllerUnit;
import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.domain.PaymentStatus;
import com.ray.pominowner.payment.dto.PaymentCreateRequest;
import com.ray.pominowner.payment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest extends ControllerUnit {

    @MockBean
    PaymentService paymentService;

    @Test
    @DisplayName("Payment가 성공적으로 생성된다.")
    public void successCreatePayment() throws Exception {
        // given
        PaymentCreateRequest request = new PaymentCreateRequest(1000, PaymentStatus.COMPLETE, PGType.TOSS);
        given(paymentService.create(any(Payment.class))).willReturn(1L);

        // when
        ResultActions actions = mvc.perform(post("/api/v1/payment").contentType(MediaType.APPLICATION_JSON).content(toJson(request)));

        // then
        actions.andExpect(status().isCreated())
                .andDo(print());
    }

}
