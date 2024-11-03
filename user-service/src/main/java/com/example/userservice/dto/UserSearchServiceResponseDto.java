package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;

import java.util.List;

public record UserSearchServiceResponseDto(
        String email,
        String name,
        String userId,
        List<OrderDto> orders
) {

    public static UserSearchServiceResponseDto of(UserEntity userEntity, List<OrderDto> orders) {
        return new UserSearchServiceResponseDto(userEntity.email, userEntity.name, userEntity.userId, orders);
    }

    public UserSearchResponseDto toUserSearchResponseDto() {
        return new UserSearchResponseDto(this.email, this.name, this.userId, this.orders);
    }
}
