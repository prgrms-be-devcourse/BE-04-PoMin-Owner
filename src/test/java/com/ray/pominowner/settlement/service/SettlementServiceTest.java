package com.ray.pominowner.settlement.service;

import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import com.ray.pominowner.orders.repository.OrderRepository;
import com.ray.pominowner.settlement.controller.dto.SettlementResponse;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;
import com.ray.pominowner.settlement.service.vo.SettlementByStoreRequest;
import com.ray.pominowner.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ray.pominowner.global.util.ExceptionMessage.INVALID_ORDER;
import static com.ray.pominowner.settlement.SettlementTestFixture.settlement;
import static com.ray.pominowner.store.StoreTestFixture.store;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @InjectMocks
    SettlementService settlementService;

    @Mock
    SettlementRepository settlementRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    StoreRepository storeRepository;

    Order order = Order.builder()
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

    @Test
    @DisplayName("Settlement 저장에 성공한다.")
    public void successCreateSettlement() {
        // given
        given(settlementRepository.save(settlement())).willReturn(settlement());

        // when
        Settlement createdSettlement = settlementService.create(settlement());

        // then
        assertThat(createdSettlement).isEqualTo(createdSettlement);
    }

    @Test
    @DisplayName("주문 정보에 따른 Settlement 조회에 성공한다.")
    public void successGetSettlementByOrder() {
        // given
        given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));
        given(settlementRepository.findSettlementByOrderIdAndDeleted(order.getId(), false)).willReturn(Optional.of(settlement()));

        // when
        Settlement settlement = settlementService.getSettlementByOrder(order.getId());

        // then
        assertThat(new SettlementResponse(settlement())).isEqualTo(new SettlementResponse(settlement));
    }

    @Test
    @DisplayName("주문이 취소된 경우 주문 정보에 따른 Settlement 조회에 실패한다.")
    public void failGetSettlementByOrderWhenCanceledOrder() {
        // given
        Order canceledOrder = Order.of(order, OrderStatus.CANCELED);
        given(orderRepository.findById(canceledOrder.getId())).willReturn(Optional.of(canceledOrder));

        // when, then
        assertThatThrownBy(() -> settlementService.getSettlementByOrder(canceledOrder.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_ORDER.getMessage());
    }

    @Test
    @DisplayName("주문이 거절된 경우 주문 정보에 따른 Settlement 조회에 실패한다.")
    public void failGetSettlementByOrderWhenRejectedOrder() {
        // given
        Order rejectedOrder = Order.of(order, OrderStatus.REJECTED);
        given(orderRepository.findById(rejectedOrder.getId())).willReturn(Optional.of(rejectedOrder));

        // when, then
        assertThatThrownBy(() -> settlementService.getSettlementByOrder(rejectedOrder.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_ORDER.getMessage());
    }

    @Test
    @DisplayName("주문이 존재하지 않는 주문 정보에 따른 Settlement 조회에 실패한다.")
    public void failGetSettlementByOrderWhenNonExistOrder() {
        // given
        given(orderRepository.findById(order.getId())).willThrow(IllegalArgumentException.class);

        // when, then
        assertThatThrownBy(() -> settlementService.getSettlementByOrder(order.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("정산이 존재하지 않는 주문 정보에 따른 Settlement 조회에 실패한다.")
    public void failGetSettlementByOrderWhenNonExistSettlement() {
        // given
        given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));
        given(settlementRepository.findSettlementByOrderIdAndDeleted(order.getId(), false)).willThrow(IllegalArgumentException.class);

        // when, then
        assertThatThrownBy(() -> settlementService.getSettlementByOrder(order.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    SettlementByStoreRequest request = new SettlementByStoreRequest(
            settlement().getStoreId(),
            "SALES",
            LocalDate.now(),
            LocalDate.now().plusDays(1)
    );

    @Test
    @DisplayName("일 정산 조회에 성공한다.")
    public void successGetDailySettlementByStore() {
        // given
        given(storeRepository.findById(request.storeId())).willReturn(Optional.of(store()));

        List<Settlement> expectedSettlements = List.of(settlement());
        given(DateType.valueOf(request.dateType()).getSettlements(settlementRepository, request)).willReturn(expectedSettlements);

        // when
        List<Settlement> settlements = settlementService.getDailySettlementByStore(request);

        // then
        assertThat(settlements).isEqualTo(expectedSettlements);
    }

    @Test
    @DisplayName("가게자 존재하지 않는 일 정산 조회에 실패한다.")
    public void failGetDailySettlementByStoreWhenNonExistStore() {
        // given
        given(storeRepository.findById(request.storeId())).willThrow(IllegalArgumentException.class);

        // when, then
        assertThatThrownBy(() -> settlementService.getDailySettlementByStore(request))
                .isInstanceOf(IllegalArgumentException.class);
    }


}
