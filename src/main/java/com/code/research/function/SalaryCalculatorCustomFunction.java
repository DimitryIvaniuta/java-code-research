package com.code.research.function;

import com.code.research.dto.Employee;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class SalaryCalculatorCustomFunction {

    public static void main(String[] args) {
        // Create an employee instance.
        Employee employee = new Employee("John Doe", 50000, 5000);

        // Lambda for a simple salary calculation: base salary plus bonus.
        EmployeeSalaryCalculator simpleCalculator = emp -> BigDecimal.valueOf(emp.getBaseSalary() + emp.getBonus());

        // Lambda for a salary calculation that deducts 20% tax from the gross salary.
        EmployeeSalaryCalculator taxDeductedCalculator = emp -> {
            double grossSalary = emp.getBaseSalary() + emp.getBonus();
            double tax = grossSalary * 0.20; // Calculate 20% tax
            return BigDecimal.valueOf(grossSalary - tax);
        };

        // Calculate and display the results.
        log.info("Simple Salary for " + employee.getName() + ": " +
                simpleCalculator.calculateSalary(employee));
        log.info("Tax Deducted Salary for " + employee.getName() + ": " +
                taxDeductedCalculator.calculateSalary(employee));
    }

}
