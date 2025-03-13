package com.code.research.function;

import com.code.research.dto.Employee;

import java.math.BigDecimal;

@FunctionalInterface
public interface EmployeeSalaryCalculator {

    BigDecimal calculateSalary(Employee employee);

}
