package com.code.research.algorithm.test.streams;

import com.code.research.algorithm.test.dto.Person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FirstPersonOlderThan30 {

    /**
     * Finds the first person in the list whose age is greater than 30.
     *
     * @param persons the list of Person objects (must not be null)
     * @return an Optional containing the first Person older than 30,
     *         or an empty Optional if none match or the list is empty
     * @throws NullPointerException if {@code persons} is null
     */
    private static Optional<Person> findFirstOlderThan30(List<Person> persons){
        Objects.requireNonNull(persons,"persons cannot be null");

        return persons.stream()
                .filter(p -> p.age() > 30)
                .findFirst();
    }

    public static void main(String[] args) {
        List<Person> roster = List.of(
                new Person("Alice", 28),
                new Person("Bob", 32),
                new Person("Carol", 25),
                new Person("Dave", 45)
        );

        Optional<Person> firstPerson = findFirstOlderThan30(roster);
        firstPerson.ifPresentOrElse(
                person -> System.out.println("Found: " + person.name() + " (" + person.age() + ")"),
                ()      -> System.out.println("No one older than 30 found.")
        );
    }
}
