package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import java.time.Month;

@Slf4j
public class ExtractMapKeysAndValues {

    public static void main(String[] args) {
        record PersonRecord(String name, LocalDateTime bookDateTimeWasBorrowed) {
        }

        // Sample Data
        Map<String, List<PersonRecord>> bookBorrowers = Map.of(
                "Java Programming", List.of(
                        new PersonRecord("Alice", LocalDateTime.of(2024, Month.MARCH, 2, 10, 0)),
                        new PersonRecord("Bob", LocalDateTime.of(2024, Month.FEBRUARY, 15, 9, 30))
                ),
                "Data Structures", List.of(
                        new PersonRecord("Charlie", LocalDateTime.of(2024, Month.MARCH, 5, 12, 15)),
                        new PersonRecord("David", LocalDateTime.of(2025, Month.MARCH, 1, 8, 45)),
                        new PersonRecord("Jason", LocalDateTime.of(2024, Month.NOVEMBER, 11, 18, 04))
                ),
                "Machine Learning", List.of(
                        new PersonRecord("Eve", LocalDateTime.of(2024, Month.JANUARY, 20, 14, 0)),
                        new PersonRecord("Frank", LocalDateTime.of(2024, Month.FEBRUARY, 10, 11, 30))
                ),
                "Empty book", new ArrayList<>()
        );

        // 1. Map where key is book name, value is a List<String> personNames
        Map<String, List<String>> bookToPersonNames = bookBorrowers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(PersonRecord::name)
                                .toList()
                ));

        // 2. Map where key is book name, value is int count of persons
        Map<String, Integer> bookToPersonCount = bookBorrowers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().size()
                ));

        // 3. Map where key is book name, value is latest time book was borrowed
        Map<String, LocalDateTime> bookToLatestBorrowTime = bookBorrowers.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(PersonRecord::bookDateTimeWasBorrowed)
                                .max(LocalDateTime::compareTo)
                                .orElseGet(() -> LocalDateTime.MIN) // Use LocalDateTime.MIN instead of null
                ));

        // 4. Map where key is first time book was borrowed, value is person name who borrowed the book
        Map<LocalDateTime, String> firstBorrowToPerson = bookBorrowers.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toMap(
                        PersonRecord::bookDateTimeWasBorrowed,
                        PersonRecord::name,
                        (existing, replacement) -> existing, // Keep existing in case of duplicates
                        TreeMap::new // Sort by borrow time
                ));

        // 5. List of all persons in the map sorted ignore case by name
        List<String> sortedPersonNames = bookBorrowers.values().stream()
                .flatMap(List::stream)
                .map(PersonRecord::name)
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        // 6. List of all books in the map sorted ignore case by bookName
        List<String> sortedBooks = bookBorrowers.keySet().stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        // Get current month and year
        Month currentMonth = LocalDateTime.now().getMonth();
        int currentYear = Year.now().getValue();

        // 7. Partitioned map where true key is bookNames borrowed current month and false otherwise
        // 7.1. Partitioned Map: Books borrowed in the current month vs. other months
        Map<Boolean, List<String>> partitionedBooksByMonth = bookBorrowers.entrySet().stream()
                .collect(Collectors.partitioningBy(
                        entry -> entry.getValue().stream()
                                .anyMatch(p -> p.bookDateTimeWasBorrowed().getMonth() == currentMonth),
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));
        // 7.2. Partitioned Map: Books borrowed in the current year vs. other years
        Map<Boolean, List<String>> partitionedBooksByYear = bookBorrowers.entrySet().stream()
                .collect(Collectors.partitioningBy(
                        entry -> entry.getValue().stream()
                                .anyMatch(p -> p.bookDateTimeWasBorrowed().getYear() == currentYear),
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                ));
        // Printing results
        log.info("1. Map<Book, List<PersonNames>>: {}", bookToPersonNames);
        log.info("2. Map<Book, CountOfPersons>: {}", bookToPersonCount);
        log.info("3. Map<Book, Latest Borrow Time>: {}", bookToLatestBorrowTime);
        log.info("4. Map<First Borrow Time, Borrower>: {}", firstBorrowToPerson);
        log.info("5. List of Sorted Person Names: {}", sortedPersonNames);
        log.info("6. List of Sorted Books: {}", sortedBooks);
        log.info("7.1. Books partitioned by current month:");
        log.info("   - Borrowed in current month: {}", partitionedBooksByMonth.get(true));
        log.info("   - Borrowed in other months: {}", partitionedBooksByMonth.get(false));

        log.info("7.2. Books partitioned by current year:");
        log.info("   - Borrowed in current year: {}", partitionedBooksByYear.get(true));
        log.info("   - Borrowed in other years: {}", partitionedBooksByYear.get(false));

    }

}
