package com.ray.pominowner.orders.controller;

import com.ray.pominowner.orders.controller.dto.ApproveOrderResponse;
import com.ray.pominowner.orders.controller.dto.ReceiveOrderRequest;
import com.ray.pominowner.orders.controller.dto.RejectOrderResponse;
import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createOrder(@RequestBody ReceiveOrderRequest request) {
        Order order = ReceiveOrderRequest.getEntity(request);

        orderService.receiveOrder(order);
    }

    @PostMapping("/orders/{orderId}/approve")
    public ApproveOrderResponse acceptOrder(@PathVariable Long orderId) {
        Order approved = orderService.approve(orderId);

        return new ApproveOrderResponse(approved);
    }

    @PostMapping("/orders/{orderId}/reject")
    public RejectOrderResponse rejectOrder(@PathVariable Long orderId) {
        Order rejected = orderService.reject(orderId);

        return new RejectOrderResponse(rejected);
    }

}
