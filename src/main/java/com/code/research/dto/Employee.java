package com.code.research.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

    private String name;

    private double baseSalary;

    private double bonus;

}
