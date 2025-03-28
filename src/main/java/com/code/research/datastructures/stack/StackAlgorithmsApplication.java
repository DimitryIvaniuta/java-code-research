package com.code.research.datastructures.stack;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;

@Slf4j
public class StackAlgorithmsApplication {

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
            double result = StackAlgorithms.evaluatePostfix(postfixExpr);
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
