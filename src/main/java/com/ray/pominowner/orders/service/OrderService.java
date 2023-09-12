package com.ray.pominowner.orders.service;

import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import com.ray.pominowner.orders.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ReceiptNumberGenerator generator;

    private final OrderRepository orderRepository;

    public Order receiveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order approve(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 주문이 없습니다"));

        Order approvedOrder = Order.of(order,
                OrderStatus.COOKING,
                generator.incrementAndGet(),
                LocalTime.now().plusMinutes(15));
        orderRepository.save(approvedOrder);

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

}
