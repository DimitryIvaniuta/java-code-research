package com.code.research.algorithm.streams;

import com.code.research.algorithm.streams.dto.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStudentsGrade {

    /**
     * Groups students by their grade and collects each group’s student names into a List.
     *
     * @param students the input List of Student
     * @return a Map from grade (String) to List of student names in that grade
     */
    public static Map<String, List<String>> groupNamesByGrade(List<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(
                        Student::getGrade,
                        Collectors.mapping(
                                Student::getName,
                                Collectors.toList()
                        )
                ));
    }

    public static void main(String[] args) {
        List<Student> roster = List.of(
                new Student("Alice",   "A"),
                new Student("Bob",     "B"),
                new Student("Carol",   "A"),
                new Student("Dave",    "C"),
                new Student("Eve",     "B")
        );

        Map<String, List<String>> byGrade = groupNamesByGrade(roster);

        byGrade.forEach((grade, names) -> {
            System.out.printf("Grade %s: %s%n", grade, names);
        });
        // Possible output:
        // Grade A: [Alice, Carol]
        // Grade B: [Bob, Eve]
        // Grade C: [Dave]
    }
}
