package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;

public record UserCreationServiceResponseDto(
        String email,
        String name,
        String userId
) {

    public static UserCreationServiceResponseDto from(UserEntity userEntity) {
        return new UserCreationServiceResponseDto(userEntity.email, userEntity.name, userEntity.userId);
    }

    public UserCreationResponseDto toUserCreationResponseDto() {
        return new UserCreationResponseDto(this.email, this.name, this.userId);
    }
}
