package com.code.research.livecoding.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class StatusUtils {

    // Define the set of valid statuses (upper-case for normalization)
    private static final Set<String> VALID_STATUSES = Set.of(
            "PENDING",
            "PROCESSING",
            "SHIPPED",
            "DELIVERED",
            "CANCELLED",
            "RETURNED"
    );

    private static List<String> validateAndNormalizeStatuses(List<String> statuses) {
        return statuses.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(VALID_STATUSES::contains)
                .toList();
    }

    // Example usage
    public static void main(String[] args) {
        List<String> rawStatuses = Arrays.asList(
                "pending", "Shipped", "unknown", " DELIVERED ", null, "cancelled", "returned", "invalid"
        );

        List<String> cleaned = validateAndNormalizeStatuses(rawStatuses);
        System.out.println(cleaned);
        // prints: [PENDING, SHIPPED, DELIVERED, CANCELLED, RETURNED]
    }

}
