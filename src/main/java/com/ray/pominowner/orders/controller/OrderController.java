package com.ray.pominowner.orders.controller;

import com.ray.pominowner.orders.controller.dto.ApproveOrderRequest;
import com.ray.pominowner.orders.controller.dto.ApproveOrderResponse;
import com.ray.pominowner.orders.controller.dto.OrderResponse;
import com.ray.pominowner.orders.controller.dto.ReceiveOrderRequest;
import com.ray.pominowner.orders.controller.dto.RejectOrderResponse;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.service.OrderService;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final PaymentService paymentService;

    @PostMapping("/orders")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createOrder(@RequestBody ReceiveOrderRequest request) {

        Order order = request.toEntity();
        Payment payment = request.toPaymentEntity();


        orderService.receiveOrder(order);
        paymentService.create(payment);
    }

    @PostMapping("/orders/{orderId}/approve")
    public ApproveOrderResponse acceptOrder(@PathVariable Long orderId, @RequestBody ApproveOrderRequest request) {
        Order approved = orderService.approve(orderId, request.cookingMinute());

        return new ApproveOrderResponse(approved);
    }

    @PostMapping("/orders/{orderId}/reject")
    public RejectOrderResponse rejectOrder(@PathVariable Long orderId) {
        Order rejected = orderService.reject(orderId);

        return new RejectOrderResponse(rejected);
    }

    @GetMapping("/orders/{storeId}/today")
    public List<OrderResponse> getTodayOrders(@PathVariable Long storeId) {
        List<Order> todayOrders = orderService.getTodayOrders(storeId);

        return todayOrders.stream()
                .map(OrderResponse::of)
                .toList();
    }

    @PostMapping("/orders/{orderId}/ready")
    public void readyOrder(@PathVariable Long orderId) {
        orderService.readyToServe(orderId);
    }

}
