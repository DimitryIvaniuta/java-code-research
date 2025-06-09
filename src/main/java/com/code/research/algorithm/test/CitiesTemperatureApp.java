package com.code.research.algorithm.test;

import java.util.Arrays;

public class CitiesTemperatureApp {

    /**
     * Prints the overall average temperature for the given cities.
     *
     * @param cities array of city names
     * @param temps  parallel array of temperatures for each city
     * @throws IllegalArgumentException if inputs are null, lengths differ, or no data
     */
    public static void PrintAvgTemp(String[] cities, int[] temps) {
        if (cities == null || temps == null) {
            throw new IllegalArgumentException("Cities and temps must not be null");
        }
        if (cities.length != temps.length) {
            throw new IllegalArgumentException(
                    "Cities and temps arrays must have the same length: " +
                            "cities.length=" + cities.length + ", temps.length=" + temps.length
            );
        }
        if (temps.length == 0) {
            throw new IllegalArgumentException("At least one city/temperature is required");
        }

        // Compute sum of temperatures
        int sum = 0;
        for (int t : temps) {
            sum += t;
        }

        // Compute average as a double
        double average = (double) sum / temps.length;

        // Print result
        System.out.printf(
                "Average temperature across %d cities %s is %.2f°C%n",
                temps.length,
                Arrays.toString(cities),
                average
        );
    }

    public static void main(String[] args) {
        String[] cities = { "Warsaw", "Kraków", "Gdańsk", "Wrocław" };
        int[] temps =    {  22,       18,       20,        19      };

        // Expected average: (22 + 18 + 20 + 19) / 4 = 19.75
        PrintAvgTemp(cities, temps);
    }
}
