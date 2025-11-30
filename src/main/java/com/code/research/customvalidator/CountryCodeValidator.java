package com.code.research.customvalidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {

    private static final Set<String> ALLOWED = Set.of("PL", "DE", "US");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // handle @NotNull separately if needed
        }
        return ALLOWED.contains(value);
    }
}
