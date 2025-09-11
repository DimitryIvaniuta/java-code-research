package com.code.research.singleton;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyApp {
    public static void main(String[] args) {
        Money income = new Money(55, "USD");
        Money expenses = new Money(55, "USD");

        boolean balanced = income.equals(expenses);
        // The default implementation of equals() in the Object class compares the identity of the object.
        log.info("Balanced: {}", balanced);
    }
}

