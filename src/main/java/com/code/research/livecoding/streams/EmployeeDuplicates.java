package com.code.research.livecoding.streams;

import com.code.research.livecoding.streams.dto.EmployeeWithId;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmployeeDuplicates {

    /**
     * Removes duplicate employees based on their ID, preserving the original order.
     * Keeps the first occurrence of each ID.
     *
     * @param employees the input list of Employee objects (must not be null)
     * @return a new List<Employee> without duplicates by ID
     * @throws NullPointerException if {@code employees} is null
     */
    public static List<EmployeeWithId> deduplicateById(List<EmployeeWithId> employees) {
        Objects.requireNonNull(employees, "employees list must not be null");
        Predicate<EmployeeWithId> distinctByIdPredicate = distinctByKey(EmployeeWithId::getId);
        return employees.stream()
                .filter(Objects::nonNull)
                .filter(distinctByIdPredicate)
                .toList();
    }


    /**
     * Returns a stateful predicate that filters out duplicate elements
     * based on a key extracted by {@code keyExtractor}.
     *
     * @param keyExtractor function to extract the comparison key from elements
     * @param <T>          the type of elements in the stream
     * @param <K>          the type of the key
     * @return a Predicate<T> that returns true the first time it sees a key,
     *         and false on subsequent encounters
     */
    private static <T, K> Predicate<T> distinctByKey(Function<? super T, K> keyExtractor) {
        Set<K> seen = new HashSet<>();
        return t -> {
            K key = keyExtractor.apply(t);
            return seen.add(key);
        };
    }

    public static void main(String[] args) {
        List<EmployeeWithId> roster = List.of(
                new EmployeeWithId(1L, "Alice"),
                new EmployeeWithId(2L, "Bob"),
                new EmployeeWithId(1L, "Alice Dup"),
                new EmployeeWithId(3L, "Carol"),
                new EmployeeWithId(2L, "Bob Dup")
        );

        List<EmployeeWithId> unique = deduplicateById(roster);
        unique.forEach(e ->
                System.out.println(e.getId() + " → " + e.getName())
        );
        // Expected output:
        // 1 → Alice
        // 2 → Bob
        // 3 → Carol
    }
}
