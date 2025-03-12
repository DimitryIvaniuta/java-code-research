package com.code.research.function;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class PredicateExample {

    public static void main(String[] args) {
        List<String> emails = Arrays.asList("user@example.com", "invalid-email", "test@domain.com");

        // Predicate to check if an email contains the "@" symbol.
        Predicate<String> isValidEmail = email -> email.contains("@");

        // Filter valid emails using the Predicate with the Stream API.
        List<String> validEmails = emails.stream()
                .filter(isValidEmail)
                .toList();
        log.info("Valid emails: " + validEmails);

        // Filter invalid emails
        // Predicate to check if an email do not contains the "@" symbol.
        Predicate<String> isInvalidEmail = email -> !email.contains("@");

        List<String> invalidEmails = emails.stream()
                .filter(isInvalidEmail)
                .toList();
        log.info("Invalid emails: " + invalidEmails);

    }

}
