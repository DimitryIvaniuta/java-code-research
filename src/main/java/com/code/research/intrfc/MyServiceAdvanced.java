package com.code.research.intrfc;

public interface MyServiceAdvanced {

    void performAbstract(String input);

    // New in Java 8:
    default void log(String input) {
        System.out.println("[LOG Advanced] " + input);
    }

}
