package com.ray.pominowner.orders.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import com.ray.pominowner.global.domain.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @Column(name = "ORDER_ID")
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

    private LocalTime estimatedCookingTime;

    private String rejectReason;

    // STORE 와 다대일 매핑
    private Long storeId;

    // Payment 와 일대일 매핑
    private Long paymentId;

    public static Order of(Order order, OrderStatus status, Integer receiptNumber, LocalTime estimatedCookingTime) {
        return Order.builder()
                .id(order.id)
                .orderNumber(order.orderNumber)
                .receiptNumber(receiptNumber)
                .status(status)
                .requestedDetails(order.requestedDetails)
                .totalPrice(order.totalPrice)
                .customerPhoneNumber(order.customerPhoneNumber)
                .reservationTime(order.reservationTime)
                .estimatedCookingTime(estimatedCookingTime)
                .storeId(order.storeId)
                .paymentId(order.paymentId)
                .build();
    }

    public static Order of(Order order, OrderStatus status, String rejectReason) {
        return Order.builder()
                .id(order.id)
                .orderNumber(order.orderNumber)
                .status(status)
                .requestedDetails(order.requestedDetails)
                .totalPrice(order.totalPrice)
                .customerPhoneNumber(order.customerPhoneNumber)
                .reservationTime(order.reservationTime)
                .rejectReason(rejectReason)
                .storeId(order.storeId)
                .paymentId(order.paymentId)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isToday() {
        LocalDate today = LocalDate.now();
        return this.createdAt.toLocalDate().equals(today);
    }

}
