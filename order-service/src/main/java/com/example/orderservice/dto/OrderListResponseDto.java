package com.example.orderservice.dto;

import java.util.List;

public record OrderListResponseDto(
        List<OrderListItemDto> orderList
) {}
