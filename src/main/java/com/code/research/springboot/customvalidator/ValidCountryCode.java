package com.code.research.springboot.customvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// e.g. value must be "PL", "DE" or "US"
@Documented
@Constraint(validatedBy = CountryCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCountryCode {

    String message() default "Invalid country code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
