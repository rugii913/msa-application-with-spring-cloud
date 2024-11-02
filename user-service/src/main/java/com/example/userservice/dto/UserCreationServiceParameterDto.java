package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;

public record UserCreationServiceParameterDto(
        String email,
        String name,
        String password
) {

    public static UserCreationServiceParameterDto from(UserCreationRequestDto userAdditionRequest) {
        return new UserCreationServiceParameterDto(userAdditionRequest.email(), userAdditionRequest.name(), userAdditionRequest.password());
    }

    public UserEntity toEntity() {
        return new UserEntity(email, name, password);
    }
}
