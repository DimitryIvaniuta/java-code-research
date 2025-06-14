package com.code.research.enums;

public interface Operation {
    double apply(double a, double b);

    default double calculateDefault(double a, double b) {
        return calculateSquareOperation(a, b);
    }

    private double calculateSquareOperation(double a, double b) {
        double res = apply(a, b);
        return Math.sqrt(res);
    }
}
