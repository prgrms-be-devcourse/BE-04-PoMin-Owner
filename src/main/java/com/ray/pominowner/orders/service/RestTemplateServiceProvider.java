package com.ray.pominowner.orders.service;

import com.ray.pominowner.orders.domain.Order;
import com.ray.pominowner.orders.service.dto.ApproveOrderNotifyRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class RestTemplateServiceProvider {

    private final RestTemplate restTemplate = new RestTemplate();

    public void notifyToApprove(int cookingMinute, Order approvedOrder) {
        URI uri = UriComponentsBuilder
                .fromUriString("http://pomin-server:8080")
                .path("/api/v1/orders/{orderNumber}")
                .encode()
                .build(approvedOrder.getOrderNumber());

        ApproveOrderNotifyRequest body = new ApproveOrderNotifyRequest(
                approvedOrder.getReceiptNumber(),
                approvedOrder.getRequestedDetails(),
                cookingMinute,
                approvedOrder.getStoreId()
        );

        RequestEntity<ApproveOrderNotifyRequest> request = RequestEntity
                .post(uri)
                .body(body);

        send(request);
    }

    private void send(RequestEntity<ApproveOrderNotifyRequest> request) {
        ResponseEntity<ApproveOrderNotifyRequest> response = restTemplate.exchange(request, ApproveOrderNotifyRequest.class);
        if (response.getStatusCode().isError()) {
            throw new IllegalStateException("고객측 서버의 응답이 없습니다");
        }
    }

}
