package com.code.research.datastructures.hash.bookmeeting.eventaddrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectAfter;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectBefore;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectEquals;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectRules;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

@Data
@Slf4j
public class CalendarEventAddRulesToCalendar implements CalendarEventAddRules {

    List<CalendarEventIntersectRules> intersectRules;

    /**
     * Constructs a CalendarEventAddRulesToCalendar instance with predefined intersection rules.
     */
    public CalendarEventAddRulesToCalendar() {
        intersectRules = List.of(
                new CalendarEventIntersectEquals(),
                new CalendarEventIntersectBefore(),
                new CalendarEventIntersectAfter()
        );
    }

    /**
     * Validates the given calendar event by ensuring it does not conflict with any existing events
     * on the same day, as determined by the defined intersection rules.
     *
     * @param eventToValidate the event to validate
     * @param eventSortedMap  a sorted map with keys as event dates and values as lists of calendar events for that day
     * @return {@code true} if the event is valid (i.e., no conflicts), {@code false} otherwise
     */
    @Override
    public boolean validate(final CalendarEvent eventToValidate,
                            final SortedMap<LocalDate, List<CalendarEvent>> eventSortedMap) {
        // Retrieve events scheduled on the same day as the event to validate.
        List<CalendarEvent> currentDayEvents = eventSortedMap
                .getOrDefault(eventToValidate.start().toLocalDate(), List.of());

        // The event is valid if none of the current day's events cause a conflict.
        return currentDayEvents.stream()
                .noneMatch(eventAssigned ->
                        intersectRules.stream().anyMatch(rule -> rule.validate(eventToValidate, eventAssigned))
                );
    }

}