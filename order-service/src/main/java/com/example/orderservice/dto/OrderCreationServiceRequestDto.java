package com.example.orderservice.dto;

import com.example.orderservice.jpa.OrderEntity;

public record OrderCreationServiceRequestDto(
        String productId,
        int quantity,
        int unitPrice,
        String userId
) {

    public OrderEntity toEntity() {
        return new OrderEntity(this.productId, this.quantity, this.unitPrice, this.userId);
    }

    public static OrderCreationServiceRequestDto of(String userId, OrderCreationRequestDto orderCreationRequestDto) {
        return new OrderCreationServiceRequestDto(
                orderCreationRequestDto.productId(),
                orderCreationRequestDto.quantity(),
                orderCreationRequestDto.unitPrice(),
                userId
        );
    }
}
