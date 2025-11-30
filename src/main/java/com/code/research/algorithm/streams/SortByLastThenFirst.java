package com.code.research.algorithm.streams;

import com.code.research.algorithm.streams.dto.Person;

import java.util.Comparator;
import java.util.List;

public class SortByLastThenFirst {

    /**
     * Returns a new list of Person sorted by last name, then first name.
     *
     * @param people the input List of Person
     * @return a List<Person> sorted by lastName, then firstName
     */
    public static List<Person> sortByLastThenFirst(List<Person> people) {
        return people.stream()
                .sorted(
                        Comparator.comparing(Person::getLastName)
                                .thenComparing(Person::getFirstName)
                )
                .toList();
    }


    public static void main(String[] args) {
        List<Person> roster = List.of(
                new Person("Alice",   "Zephyr"),
                new Person("Bob",     "Anderson"),
                new Person("Carol",   "Anderson"),
                new Person("Dave",    "Zimmerman"),
                new Person("Eve",     "Brown")
        );

        List<Person> sorted = sortByLastThenFirst(roster);
        sorted.forEach(p ->
                System.out.printf("%s %s%n", p.getFirstName(), p.getLastName())
        );
        // Prints:
        // Bob Anderson
        // Carol Anderson
        // Eve Brown
        // Alice Zephyr
        // Dave Zimmerman
    }

}
