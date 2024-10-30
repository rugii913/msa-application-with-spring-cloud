package com.example.userservice.vo;

import java.time.LocalDateTime;

public record OrderResponse(
        String productId,
        int quantity,
        int unitPrice,
        int totalPrice,
        LocalDateTime createdAt,
        String orderId
) {
}
