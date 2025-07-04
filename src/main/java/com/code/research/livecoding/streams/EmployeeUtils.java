package com.code.research.livecoding.streams;

import java.util.List;

public class EmployeeUtils {

    private static List<String> getEmailsOfEmployeesOlderThan30(List<Employee> employees){
        return employees.stream()
                .filter(e->e.age()>30)
                .map(e->e.name())
                .toList();
    }

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee("alice@example.com", 28),
                new Employee("bob@example.com", 35),
                new Employee("carol@example.com", 42)
        );

        List<String> emails = getEmailsOfEmployeesOlderThan30(employees);
        System.out.println(emails); // prints [bob@example.com, carol@example.com]
    }
}
