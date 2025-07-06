package com.code.research.livecoding.streams;

import com.code.research.livecoding.streams.dto.EmployeeWithRole;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupNamesByRole {

    /**
     * Groups employees by their role and collects each group’s employee names into a Set.
     *
     * @param employees the input List of Employee
     * @return a Map from role (String) to Set of employee names in that role
     */
    public static Map<String, Set<String>> groupNamesByRole(List<EmployeeWithRole> employees) {
        return employees.stream()
                .collect(
                        Collectors.groupingBy(
                                EmployeeWithRole::getRole,
                                Collectors.mapping(
                                        EmployeeWithRole::getName,
                                        Collectors.toSet()
                                )
                        )
                );
    }

    // Example usage
    public static void main(String[] args) {
        List<EmployeeWithRole> team = List.of(
                new EmployeeWithRole("Alice", "Developer"),
                new EmployeeWithRole("Bob", "Developer"),
                new EmployeeWithRole("Carol", "QA"),
                new EmployeeWithRole("Dave", "Developer"),
                new EmployeeWithRole("Eve", "QA")
        );

        Map<String, Set<String>> byRole = groupNamesByRole(team);

        byRole.forEach((role, names) ->
                System.out.printf("Role %s: %s%n", role, names)
        );
        // Possible output:
        // Role Developer: [Alice, Bob, Dave]
        // Role QA: [Carol, Eve]
    }

}
