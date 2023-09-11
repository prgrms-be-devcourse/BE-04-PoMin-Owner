package com.ray.pominowner.orders.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import com.ray.pominowner.global.domain.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "ORDERS")
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue
    private Long id;

    private String orderNumber; // 주문 번호

    private Integer receiptNumber; // 접수 번호

    @Enumerated(value = STRING)
    private OrderStatus status;

    private String requestedDetails;

    private Integer totalPrice;

    @Embedded
    private PhoneNumber customerPhoneNumber;

    private LocalDateTime reservationTime;

    // STORE 와 다대일 매핑
    private Long storeId;

}
