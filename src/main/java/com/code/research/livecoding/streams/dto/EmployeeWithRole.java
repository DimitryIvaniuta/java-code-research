package com.code.research.livecoding.streams.dto;

public class EmployeeWithRole {

    private final String name;
    private final String role;

    public EmployeeWithRole(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

}
