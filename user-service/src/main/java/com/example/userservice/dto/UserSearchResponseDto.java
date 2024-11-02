package com.example.userservice.dto;

import com.example.userservice.vo.OrderResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // record class를 사용할 경우 사실상 필요 없을 듯함
public record UserSearchResponseDto(
        String email,
        String name,
        String userId,
        List<OrderResponse> orders
) {}
