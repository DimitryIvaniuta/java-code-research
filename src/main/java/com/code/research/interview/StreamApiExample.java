package com.code.research.interview;

import java.util.List;
import java.util.Comparator;

public class StreamApiExample {

    public enum Status {
        ACTIVE, INACTIVE
    }
    record Employee(int id, String name, double salary, Status status) {}

    public double totalEmployers(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.status() == Status.ACTIVE)
                .sorted(Comparator.comparing(Employee::salary))
                .mapToDouble(Employee::salary)
                .sum();

    }
}
