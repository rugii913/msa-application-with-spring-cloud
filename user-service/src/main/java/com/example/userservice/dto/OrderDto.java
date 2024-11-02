package com.example.userservice.dto;

import java.time.LocalDateTime;

public record OrderDto(
        String productId,
        int quantity,
        int unitPrice,
        int totalPrice,
        LocalDateTime createdAt,
        String orderId
) {}
