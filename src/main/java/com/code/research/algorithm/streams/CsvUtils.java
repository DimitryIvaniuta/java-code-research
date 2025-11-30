package com.code.research.algorithm.streams;

import java.util.List;
import java.util.stream.Collectors;

public class CsvUtils {

    /**
     * Builds a single CSV line from the given list of values.
     *
     * @param values the input List of String values
     * @return a String representing one CSV line, with values separated by commas
     */
    public static String buildCsvLine(List<String> values) {
        return values.stream().collect(
                Collectors.joining(",")
        );
    }

    public static void main(String[] args) {
        List<String> headers = List.of("id", "name", "email");
        List<String> row     = List.of("1001", "Alice", "alice@example.com");

        String headerLine = buildCsvLine(headers);
        String rowLine    = buildCsvLine(row);

        System.out.println(headerLine);
        // prints: id,name,email

        System.out.println(rowLine);
        // prints: 1001,Alice,alice@example.com
    }
}
