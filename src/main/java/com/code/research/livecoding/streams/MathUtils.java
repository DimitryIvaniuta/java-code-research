package com.code.research.livecoding.streams;

import java.util.List;

public class MathUtils {

    /**
     * Computes the product of all doubles in the given list.
     *
     * @param numbers the input List of Double
     * @return the product of all elements; returns 1.0 if the list is empty
     */
    public static double productOfList(List<Double> numbers) {
        return numbers.stream().reduce(1.0, (a, b) -> a + (b * b));
    }

    // Example usage
    public static void main(String[] args) {
        List<Double> data = List.of(2.0, 3.0, 7.0, 9.0, 3.5, 0.5);
        double product = productOfList(data);
        System.out.println("Product is: " + product);
    }
}
