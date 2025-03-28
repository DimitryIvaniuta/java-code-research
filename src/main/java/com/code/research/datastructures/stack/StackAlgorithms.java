package com.code.research.datastructures.stack;

import lombok.extern.slf4j.Slf4j;
import java.util.Stack;

/**
 * Stack-based algorithms:
 * 1. Evaluating a postfix expression.
 * 2. Implementing an undo/redo mechanism.
 */
@Slf4j
public class StackAlgorithms {

    private StackAlgorithms() {
        //
    }

    /**
     * Evaluates a postfix (Reverse Polish Notation) expression.
     *
     * @param expression the postfix expression as a space-separated string
     * @return the computed result as a double
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static double evaluatePostfix(String expression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.trim().split("\\s+");
        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                // Pop two operands for the operator.
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression: insufficient operands for operator " + token);
                }
                double b = stack.pop();
                double a = stack.pop();
                double result = applyOperator(a, b, token);
                stack.push(result);
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: leftover operands in the stack");
        }
        return stack.pop();
    }

    /**
     * Helper method to check if a string can be parsed as a number.
     *
     * @param str the string to check
     * @return true if the string represents a numeric value; false otherwise
     */
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Applies the operator on two operands.
     *
     * @param a        the first operand
     * @param b        the second operand
     * @param operator the operator as a string (supports +, -, *, /)
     * @return the result of applying the operator
     * @throws UnsupportedOperationException if an unsupported operator is provided
     */
    private static double applyOperator(double a, double b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero encountered");
                }
                yield a / b;
            }
            default -> throw new UnsupportedOperationException("Operator not supported: " + operator);
        };
    }

}
