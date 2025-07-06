package com.code.research.livecoding.streams;

import com.code.research.livecoding.streams.dto.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class SalaryUtils {

    /**
     * Computes the average salary of the given employees.
     *
     * @param employees the input list of Employee
     * @return the average salary, or 0.0 if the list is empty
     */
    public static double averageSalary(List<Employee> employees) {
        return employees.stream().collect(Collectors.averagingDouble(Employee::salary));
    }

    // Example usage
    public static void main(String[] args) {
        List<Employee> team = List.of(
                new Employee("alice@example.com", 23, 45_000.0),
                new Employee("bob@example.com", 23,  60_000.0),
                new Employee("carol@example.com", 23, 55_000.0)
        );

        double avg = averageSalary(team);
        System.out.printf("Average salary: %.2f%n", avg);
        // prints: Average salary: 53333.33
    }
}
