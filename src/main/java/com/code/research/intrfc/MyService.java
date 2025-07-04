package com.code.research.intrfc;

public interface MyService {

    void perform(String input);

    // New in Java 8:
    default void log(String input) {
        System.out.println("[LOG] " + input);
    }

    static int calculate(int a, int b) {
        return a + b;
    }

}
