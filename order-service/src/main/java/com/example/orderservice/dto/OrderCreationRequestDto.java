package com.example.orderservice.dto;

public record OrderCreationRequestDto(
        String productId,
        int quantity,
        int unitPrice
) {}
