package com.code.research.algorithm.test.streams;

import com.code.research.algorithm.test.dto.Employee;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SortedEmployeeNames {

    /**
     * Returns a sorted list of employee names.
     *
     * @param employees the input list of Employee objects
     * @return a List<String> of employee names in ascending order
     */
    public static List<String> getSortedEmployeesNames(List<Employee> employees) {
        return employees.stream()
                .map(Employee::name)
                .sorted()
                .toList();
    }

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee("Charlie", 10000),
                new Employee("alice", 20000),
                new Employee("Bob", 30000)
        );

        List<String> sortedNames = getSortedEmployeesNames(employees);
        log.info("Sorted names: {}", String.join("; ", sortedNames));
    }

}
