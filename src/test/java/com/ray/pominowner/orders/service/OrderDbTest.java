package com.ray.pominowner.orders.service;

import com.ray.pominowner.global.domain.PhoneNumber;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class OrderDbTest {

    @Autowired
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setup() {
        order = Order.builder()
                .id(1L)
                .orderNumber("ORDERNUMBER")
                .status(OrderStatus.CONFIRMING)
                .requestedDetails("덜맵게 해주세요")
                .totalPrice(30000)
                .customerPhoneNumber(new PhoneNumber("01012345678"))
                .storeId(1L)
                .build();
    }

    @Test
    @DisplayName("오늘의 주문 목록을 DB에서 가져오는데 성공한다")
    void successGetTodayOrders() {
        // given
        Order savedOrder = orderService.receiveOrder(order);

        // when
        List<Order> todayOrders = orderService.getTodayOrders(1L);

        // then
        Order firstElement = todayOrders.get(0);
        assertThat(firstElement).isEqualTo(savedOrder);
        assertThat(todayOrders.size()).isEqualTo(1);
        assertThat(savedOrder.getCreatedAt().toLocalDate()).isEqualTo(firstElement.getCreatedAt().toLocalDate());
    }

}
