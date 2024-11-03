package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;

public record UserCreationServiceRequestDto(
        String email,
        String name,
        String password
) {

    public static UserCreationServiceRequestDto from(UserCreationRequestDto userCreationRequestDto) {
        return new UserCreationServiceRequestDto(
                userCreationRequestDto.email(),
                userCreationRequestDto.name(),
                userCreationRequestDto.password()
        );
    }

    public UserEntity toEntity() {
        return new UserEntity(email, name, password);
    }
}
