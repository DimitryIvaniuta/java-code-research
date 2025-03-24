package com.code.research.datastructures.arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DailySchedule {

    public static void main(String[] args) {
        // Array representing hourly slots in a day (e.g., 9 slots for a workday)
        String[] schedule = new String[9];
        schedule[0] = "Meeting with Team";
        schedule[1] = "Code Review";
        schedule[2] = "Project Planning";
        schedule[3] = "Lunch Break";
        schedule[4] = "Development Work";
        schedule[5] = "Client Call";
        schedule[6] = "Bug Fixing";
        schedule[7] = "Report Writing";
        schedule[8] = "Wrap-up";

        for (String s : schedule) {
            log.info("Hour {}:00 - ", s);
        }
    }

}
