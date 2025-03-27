package com.code.research.datastructures.hash.bookmeeting.eventaddrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

@Data
@Slf4j
public class CalendarEventAddRulesValidateDates implements CalendarEventAddRules {

    public boolean validate(final CalendarEvent eventToValidate,
                            final SortedMap<LocalDate, List<CalendarEvent>> eventSortedMap) {
        boolean result = eventToValidate.start() != null && eventToValidate.end() != null
                && !eventToValidate.start().isEqual(eventToValidate.end())
                && !eventToValidate.start().isAfter(eventToValidate.end());
        log.info("Event validated: " + result);
        return result;

    }

}
