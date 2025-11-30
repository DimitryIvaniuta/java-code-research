package com.code.research.springboot.customvalidator;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String email,
        @ValidCountryCode String country
) {}
