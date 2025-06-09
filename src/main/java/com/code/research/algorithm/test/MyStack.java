package com.code.research.algorithm.test;

public class MyStack<T> {
    private T[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyStack() {
        // Java doesn't allow new T[], so we create an Object[] and cast
        elements = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Pushes an element onto the top of this stack.
     * @param value the element to push
     */
    public void push(T value) {
        ensureCapacity();
        elements[size++] = value;
    }

    /**
     * Removes and returns the element at the top of this stack.
     * @return the popped element
     * @throws IllegalStateException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Pop from empty stack");
        }
        T value = elements[--size];
        elements[size] = null; // avoid memory leak
        return value;
    }

    /**
     * Returns (but does not remove) the element at the top of this stack.
     * @return the element on top
     * @throws IllegalStateException if the stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Peek from empty stack");
        }
        return elements[size - 1];
    }

    /**
     * @return true if the stack contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of elements in the stack
     */
    public int size() {
        return size;
    }

    // Double the array size when full
    private void ensureCapacity() {
        if (size == elements.length) {
            T[] newArr = (T[]) new Object[elements.length * 2];
            System.arraycopy(elements, 0, newArr, 0, elements.length);
            elements = newArr;
        }
    }
}
