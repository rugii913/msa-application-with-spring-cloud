package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "Email cannot be null.") @Size(min = 2, message = "Email should be more than 1 characters.") @Email
        String email,
        @NotNull(message = "Email cannot be null")
        @NotNull(message = "Password cannot be null.") @Size(min = 8, message = "Password should be more than 7 characters.")
        String password
) {}
