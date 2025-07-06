package com.code.research.livecoding.streams;

import com.code.research.livecoding.streams.dto.EmployeeWithDepartment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeGroupByDepartment {

    /**
     * Groups the given list of employees by their department.
     *
     * @param employees the input List<Employee>
     * @return a Map from department name to List<Employee> in that department
     */
    public static Map<String, List<EmployeeWithDepartment>> groupByDepartment(
            List<EmployeeWithDepartment> employees) {
        return employees.stream().collect(
                Collectors.groupingBy(
                        EmployeeWithDepartment::department
                )
        );
    }

    // Example usage
    public static void main(String[] args) {
        List<EmployeeWithDepartment> team = List.of(
                new EmployeeWithDepartment("Alice", "Engineering", 30, 75000.0),
                new EmployeeWithDepartment("Bob", "HR", 30, 50000),
                new EmployeeWithDepartment("Carol", "Engineering", 30, 80000),
                new EmployeeWithDepartment("Dave", "Marketing", 30, 60000),
                new EmployeeWithDepartment("Eve", "HR", 30, 55000)
        );

        Map<String, List<EmployeeWithDepartment>> byDept = groupByDepartment(team);
        byDept.forEach((dept, emps) -> {
            System.out.println("Department: " + dept);
            emps.forEach(e -> System.out.println("  " + e));
        });
    }

}
