package com.ray.pominowner.orders.service;

import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.domain.OrderStatus;
import com.ray.pominowner.orders.repository.OrderRepository;
import com.ray.pominowner.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ReceiptNumberGenerator generator;

    private final OrderRepository orderRepository;

    private final PaymentService paymentService;

    private final RestTemplateServiceProvider restTemplateServiceProvider;

    public Order receiveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order approve(Long orderId, int cookingMinute) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 주문이 없습니다"));

        Order approvedOrder = Order.of(order,
                OrderStatus.COOKING,
                generator.incrementAndGet(),
                order.getOrderedAt().toLocalTime().plusMinutes(cookingMinute));

        orderRepository.save(approvedOrder);

        restTemplateServiceProvider.notifyToApprove(cookingMinute, approvedOrder);

        return approvedOrder;
    }

    public Order reject(Long orderId, String rejectReason) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 주문이 없습니다"));

        Order rejectedOrder = Order.of(order,
                OrderStatus.REJECTED,
                rejectReason);

        orderRepository.save(rejectedOrder);
        paymentService.cancel(order.getPaymentId());

        return rejectedOrder;
    }

    public Order readyToServe(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 주문이 없습니다"));

        Order readyOrder = Order.of(order, OrderStatus.READY);

        orderRepository.save(readyOrder);

        // 향후 restTemplate 적용 예정

        return readyOrder;
    }

    public List<Order> getTodayOrders(Long storeId) {
        List<Order> storeOrders = orderRepository.findAllByStoreId(storeId);

        return storeOrders.stream()
                .filter(Order::isToday)
                .toList();
    }

}
