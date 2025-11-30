package com.code.research.customvalidator;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String email,
        @ValidCountryCode String country
) {}
