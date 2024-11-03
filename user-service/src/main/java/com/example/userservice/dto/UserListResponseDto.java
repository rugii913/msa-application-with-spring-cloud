package com.example.userservice.dto;

import java.util.List;

public record UserListResponseDto(
        List<UserListItemDto> userList
) {}
