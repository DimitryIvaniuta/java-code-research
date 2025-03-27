package com.code.research.datastructures.hash.bookmeeting;

import java.time.ZonedDateTime;

public record CalendarEvent(
        ZonedDateTime start,
        ZonedDateTime end
) implements Comparable<CalendarEvent> {

    @Override
    public int compareTo(CalendarEvent other) {
        return this.start().compareTo(other.start());
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
