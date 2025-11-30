package com.code.research.algorithm.streams.dto;

public class Student {

    private final String name;
    private final String grade;

    public Student(String name, String grade) {
        this.name  = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

}
