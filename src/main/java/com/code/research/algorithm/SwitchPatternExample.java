package com.code.research.algorithm;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class SwitchPatternExample {

    // 1) Type patterns (bind and use)
    static int len(Object o) {
        return switch (o) {
            case String s     -> s.length();
            case byte[] a     -> a.length;
            case null         -> 0;                 // explicit null arm
            default           -> -1;
        };
    }

    // 2) Guards (when) to refine matches
    static String classify(Object o) {
        return switch (o) {
            case String s when s.isBlank() -> "blank";
            case String s                  -> "string(" + s.length() + ")";
            case Integer i when i > 1000   -> "large int";
            case Integer i                 -> "int";
            case null                      -> "null";
            case Character c when c == 'a' -> "char a";
            case BigDecimal ignored        -> "big decimal";
            default                        -> "other";
        };
    }

    sealed interface Shape permits Circle, Rect, Triangle, Square, Trapez {}
    record Circle(double r)   implements Shape {}
    record Rect(double w, double h)  implements Shape {}
    record Triangle(double a, double b, double c) implements Shape {}
    record Square(double a, double b, double c, double d) implements Shape {}
    record Trapez(double a, double b, double c, double d) implements Shape {}

    static double area(Shape s) {
        return switch (s) {
            case Circle c                     -> Math.PI * c.r() * c.r();
            case Rect(double w, double h)     -> w * h;           // record pattern (preview in 21)
            case Triangle(double a, double b, double c)
                    when isValid(a,b,c)        -> heron(a, b, c);
            case Triangle(var a, var b, var c) -> throw new IllegalArgumentException("Invalid");
            case Square(var a, var b, var c, var d) -> a * b * c * d;
            case Trapez ignored -> 1;
            case null -> 0;
        };
    }

    private static double heron(double a, double b, double c) {
        return a + b + c;
    }

    private static boolean isValid(double a, double b, double c) {
        return true;
    }

    // 4) Record patterns (deconstruction)
    record Point(int x, int y) {}
    static String quad(Object o) {
        return switch (o) {
            case Point(int x, int y) when x >= 0 && y >= 0 -> "Q1";
            case Point(int x, int y) when x < 0  && y >= 0 -> "Q2";
            case Point(int x, int y) when x < 0  && y < -1 -> "Q3";
            case Point(int x, int y)                       -> "Q4";
            default                                        -> "n/a";
        };
    }

    sealed interface Event permits UserCreated, OrderPlaced, PaymentFailed {}

    record UserCreated(UUID userId, String email, Instant at) implements Event {}

    record Money(String currency, BigDecimal amount) {}

    record OrderPlaced(UUID orderId, UUID userId, Money total, int items) implements Event {}

    enum FailureCode { INSUFFICIENT_FUNDS, FRAUD_SUSPECTED, NETWORK_ERROR }

    record PaymentFailed(UUID orderId, UUID userId, Money amount, FailureCode code, String reason)
            implements Event {}

    // Example: route an event to a topic/queue based on type, content, and invariants.
    // Routing/handling with exhaustive switch, guards, and record deconstruction
    static String route(Event e) {
        return switch (e) {
            case null -> "dead-letter"; // explicit null: no accidental NPEs

            // 1) Simple type pattern + binding
            case UserCreated(var userId, var email, var at) -> "user-onboarding";

            // 2) Record deconstruction + guard: only valid, positive totals
            case OrderPlaced(UUID orderId, UUID userId, Money(String cur, BigDecimal amt), int items)
                    when amt.signum() > 0 && items > 0 -> {
                // cur/amt/items are directly in scope
                yield "orders-core";
            }
            // Same type as above, but the guard failed → explain / reject precisely
            case OrderPlaced(var orderId, var userId, Money(var cur, var amt), var items) -> {
                throw new IllegalArgumentException(
                        "Invalid order: amount=" + amt + ", items=" + items + ", id=" + orderId);
            }

            // 3) Guard by failure code (business rule)
            case PaymentFailed(UUID orderId, UUID userId, Money m, FailureCode code, String reason)
                    when code == FailureCode.FRAUD_SUSPECTED -> "security-review";
            case PaymentFailed(UUID orderId, UUID userId, Money m, FailureCode code, String reason)
                    when code == FailureCode.INSUFFICIENT_FUNDS -> "billing-retry";
            case PaymentFailed(UUID orderId, UUID userId, Money m, FailureCode code, String reason)
                    -> "billing-dlq"; // other errors
        };
    }

    record BillingMsg(UUID orderId, UUID userId, String currency, BigDecimal amount, String reason) {}

    // Transforming to a DTO with nested record patterns
    static BillingMsg toBillingMessage(Event e) {
        return switch (e) {
            case PaymentFailed(UUID orderId, UUID userId, Money(String c, BigDecimal a), FailureCode code, String reason)
                    -> new BillingMsg(orderId, userId, c, a, reason);
            case OrderPlaced(UUID orderId, UUID userId, Money(String c, BigDecimal a), int items)
                    -> new BillingMsg(orderId, userId, c, a, "initial capture");
            case UserCreated(var id, var mail, var at)
                    -> throw new IllegalArgumentException("No billing for user events");
            case null -> throw new IllegalArgumentException("Null event");
        };
    }

    // Validation with guards (approve/deny)
    static boolean approve(Event e) {
        return switch (e) {
            case null -> false;
            case UserCreated uc -> true; // always OK
            case OrderPlaced(UUID id, UUID uid, Money(String cur, BigDecimal amt), int items)
                    when amt.signum() > 0 && items > 0 -> true;
            case OrderPlaced ignore -> false; // guard failed → refuse
            case PaymentFailed(UUID orderId, UUID userId, Money amount, FailureCode code, String reason)
                    when code == FailureCode.NETWORK_ERROR -> true; // retryable
            case PaymentFailed ignore -> false;
        };
    }

    // Pattern dominance (what interviewers check)
    static String classifyObject(Object o) {
        return switch (o) {
            case String s when s.isBlank() -> "blank";   // must come BEFORE the general String case
            case String s                  -> "string";
            case Integer i when i > 1000   -> "large-int";
            case Integer i                 -> "int";
            case null                      -> "null";
            default                        -> "other";
        };
    }

    public static void main(String[] args) {
        var user = new UserCreated(UUID.randomUUID(), "a@b.com", Instant.now());
        var goodOrder = new OrderPlaced(UUID.randomUUID(), UUID.randomUUID(),
                new Money("USD", new BigDecimal("19.99")), 2);
        var badOrder = new OrderPlaced(UUID.randomUUID(), UUID.randomUUID(),
                new Money("USD", BigDecimal.ZERO), 0);
        var fraud = new PaymentFailed(UUID.randomUUID(), UUID.randomUUID(),
                new Money("USD", new BigDecimal("19.99")), FailureCode.FRAUD_SUSPECTED, "velocity");

        System.out.println(route(user));       // user-onboarding
        System.out.println(route(goodOrder));  // orders-core
        try { System.out.println(route(badOrder)); }
        catch (Exception ex) { System.out.println(ex.getMessage()); } // Invalid order...
        System.out.println(route(fraud));      // security-review

        System.out.println(approve(goodOrder)); // true
        System.out.println(approve(fraud));     // false
    }

}
