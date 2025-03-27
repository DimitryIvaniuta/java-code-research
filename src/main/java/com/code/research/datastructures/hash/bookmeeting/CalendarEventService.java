package com.code.research.datastructures.hash.bookmeeting;

import com.code.research.datastructures.hash.bookmeeting.eventaddrules.CalendarEventAddRules;
import com.code.research.datastructures.hash.bookmeeting.eventaddrules.CalendarEventAddRulesToCalendar;
import com.code.research.datastructures.hash.bookmeeting.eventaddrules.CalendarEventAddRulesValidateDates;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class CalendarEventService {

    private final SortedMap<LocalDate, List<CalendarEvent>> eventSortedMap;
    private final List<CalendarEventAddRules> addEventRules;

    public CalendarEventService() {
        this.eventSortedMap = new TreeMap<>();
        this.addEventRules = List.of(
                new CalendarEventAddRulesValidateDates(),
                new CalendarEventAddRulesToCalendar()
        );
    }

    private boolean storeEvent(final CalendarEvent eventToAdd) {
        LocalDate eventDate = eventToAdd.start().toLocalDate();
        eventSortedMap.computeIfAbsent(eventDate, date -> new ArrayList<>()).add(eventToAdd);
        log.info("Stored event on {}: {}", eventDate, eventToAdd);
        return true;
    }

    public boolean addEvent(final CalendarEvent eventToAdd) {
        boolean isValid = addEventRules.stream()
                .allMatch(rule -> rule.validate(eventToAdd, eventSortedMap));
        log.info("Event validation result: {}", isValid);
        return isValid && storeEvent(eventToAdd);
    }

    public SortedMap<LocalDate, List<CalendarEvent>> getAllEvents() {
        return eventSortedMap;
    }
}
