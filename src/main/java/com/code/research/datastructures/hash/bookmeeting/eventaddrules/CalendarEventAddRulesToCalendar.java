package com.code.research.datastructures.hash.bookmeeting.eventaddrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectAfter;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectBefore;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectEquals;
import com.code.research.datastructures.hash.bookmeeting.intersectrules.CalendarEventIntersectRules;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Data
@Slf4j
public class CalendarEventAddRulesToCalendar implements CalendarEventAddRules {

    List<CalendarEventIntersectRules> intersectRules;

    public CalendarEventAddRulesToCalendar() {
        intersectRules = List.of(
                new CalendarEventIntersectEquals(),
                new CalendarEventIntersectBefore(),
                new CalendarEventIntersectAfter()
        );
    }

    @Override
    public boolean validate(final CalendarEvent eventToValidate,
                            final SortedMap<LocalDate, List<CalendarEvent>> eventSortedMap) {
        boolean valid = true;
        List<CalendarEvent> currentDayEvents = eventSortedMap.getOrDefault(eventToValidate.start().toLocalDate(), new ArrayList<>());
        for (CalendarEvent eventAssigned : currentDayEvents) {
            if (valid) {
                valid = intersectRules.stream().noneMatch(rule -> rule.validate(eventToValidate, eventAssigned));
            }
        }
        log.info("Event validate: {}", valid);
        return valid;
    }

}