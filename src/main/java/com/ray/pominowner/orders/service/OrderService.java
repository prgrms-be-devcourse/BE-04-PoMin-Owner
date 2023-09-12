package com.ray.pominowner.orders.service;

import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import com.ray.pominowner.orders.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@Transactional
public class OrderService {

    private static Integer RECEIPT_NUMBER = 1;

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository repository) {
        this.orderRepository = repository;
    }

    public Order receiveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order approve(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 주문이 없습니다"));

        Order approvedOrder = Order.of(order,
                OrderStatus.COOKING,
                RECEIPT_NUMBER,
                LocalTime.now().plusMinutes(15));
        orderRepository.save(approvedOrder);

        updateReceiptNumber();

        return approvedOrder;
    }

    public Order reject(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 주문이 없습니다"));

        Order rejectedOrder = Order.of(order,
                OrderStatus.REJECTED,
                "재고 소진");
        orderRepository.save(rejectedOrder);

        return rejectedOrder;
    }

    private void updateReceiptNumber() {
        RECEIPT_NUMBER = RECEIPT_NUMBER % 999 + 1; // 1 ~ 999
    }

}
