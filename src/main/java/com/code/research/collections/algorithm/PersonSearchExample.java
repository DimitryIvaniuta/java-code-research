package com.code.research.collections.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class PersonSearchExample {

    // Define a simple Person class.
    static class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }

    public static void main(String[] args) {
        // Create a list of Person objects.
        List<Person> persons = new ArrayList<>(List.of(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35),
                new Person("Diana", 28)
        ));

        // Define a comparator that compares Person objects based on their name.
        Comparator<Person> nameComparator = Comparator.comparing(Person::getName);

        // Sort the list using the comparator.
        persons.sort(nameComparator);
        log.info("Sorted persons: {}", persons);

        // Create a key Person object for searching. The 'age' is irrelevant here since the comparator only compares names.
        Person searchKey = new Person("Charlie", 0);

        // Perform binary search using the sorted list and the same comparator.
        int index = Collections.binarySearch(persons, searchKey, nameComparator);

        // Check if the key was found.
        if (index >= 0) {
            log.info("Found: {}", persons.get(index));
        } else {
            log.info("Person not found. Insertion point: {}", -index - 1);
        }
    }

}
