package com.code.research.algorithm.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    @Test
    void testPushAndPop() {
        MyStack<Integer> stack = new MyStack<>();

        assertTrue(stack.isEmpty(), "Stack should be empty initially");
        assertEquals(0, stack.size(), "Size should be 0 initially");

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertFalse(stack.isEmpty(), "Stack should not be empty after pushes");
        assertEquals(3, stack.size(), "Size should be 3 after three pushes");
        assertEquals(30, stack.peek(), "Peek should return the last pushed element");

        int popped = stack.pop();
        assertEquals(30, popped, "Pop should return the last pushed element");
        assertEquals(2, stack.size(), "Size should decrement after pop");
        assertEquals(20, stack.peek(), "Peek should now return the new top");

        // Pop remaining elements
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty(), "Stack should be empty after popping all elements");
    }

    @Test
    void testPopOnEmptyThrows() {
        MyStack<String> stack = new MyStack<>();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                stack::pop,
                "Popping empty stack should throw"
        );
        assertEquals("Pop from empty stack", ex.getMessage());
    }

    @Test
    void testPeekOnEmptyThrows() {
        MyStack<String> stack = new MyStack<>();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                stack::peek,
                "Peeking empty stack should throw"
        );
        assertEquals("Peek from empty stack", ex.getMessage());
    }
}