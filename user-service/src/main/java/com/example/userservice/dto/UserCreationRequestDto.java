package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;
import com.example.userservice.vo.UserCreationRequest;

public record UserCreationRequestDto(
        String email,
        String name,
        String password
) {

    public static UserCreationRequestDto from(UserCreationRequest userAdditionRequest) {
        return new UserCreationRequestDto(userAdditionRequest.email(), userAdditionRequest.name(), userAdditionRequest.password());
    }

    public UserEntity toEntity() {
        return new UserEntity(email, name, password);
    }
}
