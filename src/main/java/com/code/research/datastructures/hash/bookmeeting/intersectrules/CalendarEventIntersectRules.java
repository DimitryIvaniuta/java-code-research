package com.code.research.datastructures.hash.bookmeeting.intersectrules;

import com.code.research.datastructures.hash.bookmeeting.CalendarEvent;

public interface CalendarEventIntersectRules {

    boolean validate(
            CalendarEvent eventToValidate,
            CalendarEvent eventAssigned
    );

}
