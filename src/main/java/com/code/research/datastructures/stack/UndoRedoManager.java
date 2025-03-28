package com.code.research.datastructures.stack;

import java.util.Stack;

/**
 * A generic Undo/Redo manager using two stacks.
 *
 * @param <T> the type representing a state
 */
public class UndoRedoManager<T> {
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
