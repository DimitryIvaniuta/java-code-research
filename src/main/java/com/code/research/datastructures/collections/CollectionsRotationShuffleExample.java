package com.code.research.datastructures.collections;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * CollectionsRotationShuffleExample demonstrates the use of the {@code Collections.rotate()} and
 * {@code Collections.shuffle()} methods in Java.
 *
 * <p>This example creates a list of strings and performs the following operations:
 * <ol>
 *   <li>Rotates the list by a specified distance. For a positive distance, elements are moved
 *       from the end to the beginning. This can be useful for scheduling or cyclically reordering elements.</li>
 *   <li>Shuffles the list to randomly permute the elements. Shuffling is useful in scenarios like randomizing
 *       order for tests, games, or user interface elements.</li>
 * </ol>
 *
 * <p>Both methods modify the list in place, and the operations are executed using Java's built-in Collections API.
 */
@Slf4j
public class CollectionsRotationShuffleExample {

    /**
     * Main method demonstrating the usage of Collections.rotate and Collections.shuffle.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a sample list of fruit names.
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        fruits.add("Elderberry");
        fruits.add("Fig");
        fruits.add("Grape");

        log.info("Original List: {}", fruits);

        // Rotate the list by 3 positions.
        // For a positive rotation, the last 3 elements are moved to the beginning of the list.
        Collections.rotate(fruits, 3);
        log.info("After rotating by 3: {}", fruits);

        // Shuffle the list to randomize the order of elements.
        Collections.shuffle(fruits);
        log.info("After shuffling: {}", fruits);
    }

}
