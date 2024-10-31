package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreationRequest(
        @NotNull(message = "Email cannot be null.") @Size(min = 2, message = "Email should be more than 1 characters.") @Email
        String email,
        @NotNull(message = "Name cannot be null.") @Size(min = 2, message = "Name should be more than 1 characters.")
        String name,
        @NotNull(message = "Password cannot be null.") @Size(min = 8, message = "Password should be more than 7 characters.")
        String password
) {}
