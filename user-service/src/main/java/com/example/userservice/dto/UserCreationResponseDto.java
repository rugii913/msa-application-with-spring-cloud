package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;

public record UserCreationResponseDto(
        String email,
        String name,
        String userId
) {

    public static UserCreationResponseDto from(UserEntity userEntity) {
        return new UserCreationResponseDto(userEntity.email, userEntity.name, userEntity.userId);
    }
}
