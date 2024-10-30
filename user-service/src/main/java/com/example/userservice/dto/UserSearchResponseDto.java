package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;
import com.example.userservice.vo.OrderResponse;
import com.example.userservice.vo.UserSearchResponse;

import java.util.List;

public record UserSearchResponseDto(
        String email,
        String name,
        String userId,
        List<OrderResponse> orders
) {

    public static UserSearchResponseDto of(UserEntity userEntity, List<OrderResponse> orders) {
        return new UserSearchResponseDto(userEntity.email, userEntity.name, userEntity.userId, orders);
    }

    public UserSearchResponse toUserSearchResponse() {
        return new UserSearchResponse(this.email, this.name, this.userId, this.orders);
    }
}
