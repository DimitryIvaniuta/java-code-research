package com.code.research.livecoding.streams;

import java.util.List;
import java.util.Optional;

public class LongestString {

    /**
     * Finds the longest string in the given list.
     *
     * @param strings the input List of String
     * @return an Optional containing the longest string, or empty if the list is empty
     */
    public static Optional<String> findLongestString(List<String> strings) {
        return strings.stream()
                .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2);
    }

    public static void main(String[] args) {
        List<String> words = List.of("apple", "banana", "cherry", "date", "elderberry");

        Optional<String> longest = findLongestString(words);
        longest.ifPresent(s ->
                System.out.println("Longest string is: " + s)
        );
        // prints: Longest string is: elderberry

        // Handling an empty list:
        List<String> empty = List.of();
        Optional<String> none = findLongestString(empty);
        System.out.println("Result for empty list: " + none);
        // prints: Result for empty list: Optional.empty
    }
}
