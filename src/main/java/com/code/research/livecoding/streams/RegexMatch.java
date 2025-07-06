package com.code.research.livecoding.streams;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class RegexMatch {
    /**
     * Finds the first ID in the list that matches the given regex.
     *
     * @param ids   the input List of String IDs
     * @param regex the regular expression to match against each ID
     * @return an Optional containing the first matching ID, or empty if none match
     */
    public static Optional<String> findFirstMatchingId(List<String> ids, String regex) {
        Pattern emailPattern = Pattern.compile(regex);
        return ids.stream()
                .filter(emailPattern.asPredicate())
                .findFirst();
    }
    
    public static void main(String[] args) {
        List<String> ids = List.of(
                "ABC-123", "foo-999", "XYZ-789", "123-ABC", "DEF-456"
        );

        // Regex: three uppercase letters, a dash, then three digits
        String regex = "^[A-Z]{3}-\\d{3}$";

        Optional<String> match = findFirstMatchingId(ids, regex);
        match.ifPresentOrElse(
                id -> System.out.println("First matching ID: " + id),
                () -> System.out.println("No ID matches the regex")
        );
        // Expected output: First matching ID: ABC-123
    }

}
