package com.code.research.datastructures.hash.bookmeeting.eventaddrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface CalendarEventAddRules {

    boolean validate(
            CalendarEvent event,
            SortedMap<LocalDate, List<CalendarEvent>> eventSortedMap
    );

}
