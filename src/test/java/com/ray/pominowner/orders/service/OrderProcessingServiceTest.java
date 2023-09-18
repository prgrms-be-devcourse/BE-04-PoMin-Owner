package com.ray.pominowner.orders.service;

import com.ray.pominowner.orders.controller.dto.ReceiveOrderRequest;
import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.payment.domain.PaymentStatus;
import com.ray.pominowner.payment.dto.PaymentCreateRequest;
import com.ray.pominowner.payment.service.PaymentService;
import com.ray.pominowner.settlement.service.SettlementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderProcessingServiceTest {

    @InjectMocks
    private OrderProcessingService orderProcessingService;

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private SettlementService settlementService;

    private final ReceiveOrderRequest request = new ReceiveOrderRequest(
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

    @Test
    @DisplayName("Order가 성공적으로 생성된다.")
    public void successCreateOrder() {
        // given
        given(orderService.receiveOrder(request.toEntity())).willReturn(request.toEntity());
        given(paymentService.create(request.toPaymentEntity())).willReturn(request.toPaymentEntity());
        given(settlementService.create(request.toSettlementEntity())).willReturn(request.toSettlementEntity());

        // when, then
        assertThatNoException()
                .isThrownBy(() -> orderProcessingService.create(request));

        verify(orderService, times(1)).receiveOrder(any());
        verify(paymentService, times(1)).create(any());
        verify(settlementService, times(1)).create(any());
    }

}
