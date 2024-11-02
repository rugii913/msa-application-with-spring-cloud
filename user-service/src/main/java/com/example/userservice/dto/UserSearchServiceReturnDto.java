package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;
import com.example.userservice.vo.OrderResponse;

import java.util.List;

public record UserSearchServiceReturnDto(
        String email,
        String name,
        String userId,
        List<OrderResponse> orders
) {

    public static UserSearchServiceReturnDto of(UserEntity userEntity, List<OrderResponse> orders) {
        return new UserSearchServiceReturnDto(userEntity.email, userEntity.name, userEntity.userId, orders);
    }

    public UserSearchResponseDto toUserSearchResponse() {
        return new UserSearchResponseDto(this.email, this.name, this.userId, this.orders);
    }
}
