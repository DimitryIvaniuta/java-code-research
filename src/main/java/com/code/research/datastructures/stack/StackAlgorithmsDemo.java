package com.code.research.datastructures.stack;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Stack-based algorithms:
 * 1. Evaluating a postfix expression.
 * 2. Implementing an undo/redo mechanism.
 */
@Slf4j
public class StackAlgorithmsDemo {

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

    /**
     * A generic Undo/Redo manager using two stacks.
     *
     * @param <T> the type representing a state
     */
    public static class UndoRedoManager<T> {
        private Stack<T> undoStack = new Stack<>();
        private Stack<T> redoStack = new Stack<>();

        /**
         * Executes a new operation by adding the new state.
         * Clears the redo stack since new operation invalidates redo history.
         *
         * @param state the new state to record
         */
        public void execute(T state) {
            undoStack.push(state);
            redoStack.clear();
        }

        /**
         * Undoes the last operation and returns the state that was undone.
         *
         * @return the undone state
         * @throws IllegalStateException if no operations can be undone
         */
        public T undo() {
            if (!canUndo()) {
                throw new IllegalStateException("No operations to undo");
            }
            T state = undoStack.pop();
            redoStack.push(state);
            return state;
        }

        /**
         * Redoes the last undone operation and returns the state that was redone.
         *
         * @return the redone state
         * @throws IllegalStateException if no operations can be redone
         */
        public T redo() {
            if (!canRedo()) {
                throw new IllegalStateException("No operations to redo");
            }
            T state = redoStack.pop();
            undoStack.push(state);
            return state;
        }

        /**
         * Checks if an undo operation can be performed.
         *
         * @return true if the undo stack is not empty; false otherwise
         */
        public boolean canUndo() {
            return !undoStack.isEmpty();
        }

        /**
         * Checks if a redo operation can be performed.
         *
         * @return true if the redo stack is not empty; false otherwise
         */
        public boolean canRedo() {
            return !redoStack.isEmpty();
        }
    }

    /**
     * Main method to demonstrate the stack-based algorithms.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Demonstrate Postfix Expression Evaluation
        String postfixExpr = "3 4 + 2 * 7 /";  // Equivalent to ((3 + 4) * 2) / 7
        log.info("Postfix Expression: " + postfixExpr);
        try {
            double result = evaluatePostfix(postfixExpr);
            log.info("Evaluated Result: " + result);
        } catch (Exception e) {
            log.error("Error evaluating expression: ", e);
        }

        // Demonstrate Undo/Redo Manager functionality.
        log.info("\nUndo/Redo Manager Demo:");
        UndoRedoManager<String> editorStateManager = new UndoRedoManager<>();

        // Simulate operations (e.g., text editor state changes)
        editorStateManager.execute("State 1: Initial Content");
        editorStateManager.execute("State 2: Added Title");
        editorStateManager.execute("State 3: Added Body Content");

        // Perform undo operations.
        if (editorStateManager.canUndo()) {
            log.info("Undo: " + editorStateManager.undo()); // Undoes "State 3: Added Body Content"
        }
        if (editorStateManager.canUndo()) {
            log.info("Undo: " + editorStateManager.undo()); // Undoes "State 2: Added Title"
        }

        // Perform a redo operation.
        if (editorStateManager.canRedo()) {
            log.info("Redo: {}", editorStateManager.redo()); // Redoes "State 2: Added Title"
        }

        Deque<String> stack = new ArrayDeque<>();

        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");

        String v1= stack.pop();
        String v2= stack.pop();

        log.info("Last: {} Prev Last: {}", v1, v2);
    }
}
