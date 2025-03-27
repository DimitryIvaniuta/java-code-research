package com.code.research.datastructures.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A calendar booking system that uses a Rule Template to determine event conflicts.
 * Each event is defined by a start (inclusive) and an end (exclusive) time.
 */
@Slf4j
public class MyCalendarWithRules {

    private final TreeMap<Integer, Integer> calendar;
    private final List<BookingRule> rules;

    /**
     * Constructs a new MyCalendarWithRules instance with predefined booking rules.
     */
    public MyCalendarWithRules() {
        calendar = new TreeMap<>();
        // Initialize the booking rules.
        rules = List.of(
                new NoConflictWithPreviousRule(),
                new NoConflictWithNextRule()
        );
    }

    /**
     * Attempts to book an event in the calendar.
     * The booking is successful only if all rules validate the new event.
     *
     * @param start the start time of the event (inclusive)
     * @param end   the end time of the event (exclusive)
     * @return {@code true} if the event is successfully booked, {@code false} if a conflict is detected
     */
    public boolean book(int start, int end) {
        // Validate the new event using all booking rules.
        boolean valid = rules.stream().allMatch(rule -> rule.validate(start, end, calendar));
        // If valid, book the event; otherwise, return false.
        return valid && bookEvent(start, end);
    }

    /**
     * Books the event by inserting it into the calendar.
     *
     * @param start the start time of the event
     * @param end   the end time of the event
     * @return always {@code true} since booking is performed only after validation
     */
    private boolean bookEvent(int start, int end) {
        calendar.put(start, end);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : calendar.entrySet()) {
            sb.append("[").append(entry.getKey()).append(", ").append(entry.getValue()).append(") ");
        }
        return sb.toString().trim();
    }

    /**
     * BookingRule interface for defining event conflict rules.
     */
    public interface BookingRule {
        /**
         * Validates the new event against the calendar.
         *
         * @param start    the start time of the new event
         * @param end      the end time of the new event
         * @param calendar the current calendar of booked events
         * @return {@code true} if the event passes this rule; {@code false} otherwise
         */
        boolean validate(int start, int end, TreeMap<Integer, Integer> calendar);
    }

    /**
     * Rule to ensure the new event does not conflict with an event that ends after its start.
     */
    public static class NoConflictWithPreviousRule implements BookingRule {
        @Override
        public boolean validate(int start, int end, TreeMap<Integer, Integer> calendar) {
            Map.Entry<Integer, Integer> previous = calendar.floorEntry(start);
            return previous == null || previous.getValue() <= start;
        }
    }

    /**
     * Rule to ensure the new event does not conflict with an event that starts before its end.
     */
    public static class NoConflictWithNextRule implements BookingRule {
        @Override
        public boolean validate(int start, int end, TreeMap<Integer, Integer> calendar) {
            Map.Entry<Integer, Integer> next = calendar.ceilingEntry(start);
            return next == null || next.getKey() >= end;
        }
    }

    /**
     * Main method to demonstrate the calendar booking with rule validation.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        MyCalendarWithRules myCalendar = new MyCalendarWithRules();

        log.info("Booking [10, 20): {}", myCalendar.book(10, 20));  // Expected: true
        log.info("Booking [15, 25): {}", myCalendar.book(15, 25));  // Expected: false (conflict with [10, 20))
        log.info("Booking [20, 30): {}", myCalendar.book(20, 30));  // Expected: true

        log.info("Current Calendar: {}", myCalendar);
    }

}
