package com.code.research.datastructures.hash.bookmeeting.intersectrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class CalendarEventIntersectBefore implements CalendarEventIntersectRules {

    @Override
    public boolean validate(final CalendarEvent eventToValidate, final CalendarEvent eventAssigned) {
        boolean result = eventToValidate.start().isBefore(eventAssigned.start()) && eventToValidate.end().isAfter(eventAssigned.start());
        log.info("Intersects After: {}", result);
        return result;
    }

}