package com.example.orderservice.dto;

import com.example.orderservice.jpa.OrderEntity;

import java.time.LocalDateTime;

public record OrderCreationResponseDto(
        String orderId,
        String productId,
        int quantity,
        int unitPrice,
        int totalPrice,
        LocalDateTime createdAt
) {

    public static OrderCreationResponseDto from(OrderEntity orderEntity) {
        return new OrderCreationResponseDto(
                orderEntity.orderId,
                orderEntity.productId,
                orderEntity.quantity,
                orderEntity.unitPrice,
                orderEntity.totalPrice,
                orderEntity.getCreatedAt()
        );
    }
}
