package com.code.research.algorithm.streams;

import com.code.research.algorithm.streams.dto.Employee;

import java.util.List;

public class SalarySum {

    /**
     * Sums all employee salaries strictly greater than the specified threshold.
     *
     * @param employees the input list of Employee
     * @param threshold the salary threshold (exclusive)
     * @return the total sum of salaries above the threshold
     */
    public static double sumSalariesAboveThreshold(List<Employee> employees, double threshold) {
        return employees.stream()
                .mapToDouble(Employee::salary)
                .filter(s -> s > threshold)
                .sum();
    }

    public static void main(String[] args) {
        List<Employee> team = List.of(
                new Employee("Alice", 20, 45000.0),
                new Employee("Bob", 20, 60000.0),
                new Employee("Carol", 20, 55000.0),
                new Employee("Dave", 20, 30000.0)
        );

        double threshold = 50000.0;
        double sum = sumSalariesAboveThreshold(team, threshold);
        System.out.printf("Sum of salaries > %.2f: %.2f%n", threshold, sum);
        // prints: Sum of salaries > 50000.00: 115000.00
    }
}
