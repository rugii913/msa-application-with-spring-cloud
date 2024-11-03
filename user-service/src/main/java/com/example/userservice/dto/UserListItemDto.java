package com.example.userservice.dto;

import com.example.userservice.jpa.UserEntity;

public record UserListItemDto(
        String email,
        String name,
        String userId
) {

    public static UserListItemDto from(UserEntity userEntity) {
        return new UserListItemDto(userEntity.email, userEntity.name, userEntity.userId);
    }
}
