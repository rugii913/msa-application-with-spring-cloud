package com.example.orderservice.dto;

import com.example.orderservice.jpa.OrderEntity;

import java.time.LocalDateTime;

public record OrderSearchResponseDto(
        String orderId,
        String productId,
        int quantity,
        int unitPrice,
        int totalPrice,
        String userId,
        LocalDateTime createdAt
) {

    public static OrderSearchResponseDto from(OrderEntity orderEntity) {
        return new OrderSearchResponseDto(
                orderEntity.orderId,
                orderEntity.productId,
                orderEntity.quantity,
                orderEntity.unitPrice,
                orderEntity.totalPrice,
                orderEntity.userId,
                orderEntity.getCreatedAt()
        );
    }
}