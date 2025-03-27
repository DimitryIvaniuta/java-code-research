package com.code.research.datastructures.hash.bookmeeting;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

@Slf4j
public class BookMeetingApplication {

    public static void main(String[] args) {
        CalendarEventService calendarEventService = new CalendarEventService();
        CalendarEvent event = new CalendarEvent(
                ZonedDateTime.of(2005, 12, 2, 13, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2005, 12, 2, 13, 30, 0, 0, ZoneId.of("UTC"))
        );
        log.info("\nAdd event: {}:{}", event, calendarEventService.addEvent(event));
        event = new CalendarEvent(
                ZonedDateTime.of(2005, 12, 2, 12, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2005, 12, 2, 13, 15, 0, 0, ZoneId.of("UTC"))
        );
        log.info("Add event: {}:{}", event, calendarEventService.addEvent(event));
        event = new CalendarEvent(
                ZonedDateTime.of(2005, 12, 2, 15, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2005, 12, 2, 16, 15, 0, 0, ZoneId.of("UTC"))
        );
        log.info("Add event: {}:{}", event, calendarEventService.addEvent(event));
        event = new CalendarEvent(
                ZonedDateTime.of(2005, 12, 2, 15, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2005, 12, 2, 16, 15, 0, 0, ZoneId.of("UTC"))
        );
        log.info("Add event: {}:{}", event, calendarEventService.addEvent(event));
        event = new CalendarEvent(
                ZonedDateTime.of(2005, 12, 2, 17, 0, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2005, 12, 2, 18, 0, 0, 0, ZoneId.of("UTC"))
        );
        log.info("Add event: {}:{}", event, calendarEventService.addEvent(event));
        event = new CalendarEvent(
                ZonedDateTime.of(2005, 12, 2, 16, 30, 0, 0, ZoneId.of("UTC")),
                ZonedDateTime.of(2005, 12, 2, 16, 55, 0, 0, ZoneId.of("UTC"))
        );
        log.info("Add event: {}:{}", event, calendarEventService.addEvent(event));
        SortedMap<LocalDate, List<CalendarEvent>> allEvents = calendarEventService.getAllEvents();
        allEvents.keySet()
                .forEach(dt -> {
                    log.info("\nDate: {}", dt);
                    List<CalendarEvent> events = allEvents.get(dt);
                    Collections.sort(events);
                    events.forEach(e -> {
                        log.info("\nEvent Created: {}", e);
                    });
                });
    }

}
