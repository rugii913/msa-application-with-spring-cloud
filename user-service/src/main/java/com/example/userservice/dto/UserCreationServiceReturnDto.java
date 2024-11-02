package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;

public record UserCreationServiceReturnDto(
        String email,
        String name,
        String userId
) {

    public static UserCreationServiceReturnDto from(UserEntity userEntity) {
        return new UserCreationServiceReturnDto(userEntity.email, userEntity.name, userEntity.userId);
    }

    public UserCreationResponseDto toUserCreationResponse() {
        return new UserCreationResponseDto(this.email, this.name, this.userId);
    }
}
