package com.code.research.datastructures.hash.bookmeeting.intersectrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class CalendarEventIntersectEquals implements CalendarEventIntersectRules {

    @Override
    public boolean validate(final CalendarEvent eventToValidate, final CalendarEvent eventAssigned) {
        boolean result = eventToValidate.start().isEqual(eventAssigned.start()) || eventToValidate.end().isEqual(eventAssigned.end());
        log.info("Intersects Equal: {}", result);
        return result;
    }

}