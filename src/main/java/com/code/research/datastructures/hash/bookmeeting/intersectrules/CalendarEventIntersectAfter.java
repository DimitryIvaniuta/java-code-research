package com.code.research.datastructures.hash.bookmeeting.intersectrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class CalendarEventIntersectAfter implements CalendarEventIntersectRules {

    @Override
    public boolean validate(final CalendarEvent eventToValidate, final CalendarEvent eventAssigned) {
        boolean result = eventToValidate.start().isAfter(eventAssigned.start())
                && eventToValidate.start().isBefore(eventAssigned.end());
        log.info("Intersects After: {}", result);
        return result;
    }

}
