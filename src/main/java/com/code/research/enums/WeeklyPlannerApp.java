package com.code.research.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

@Slf4j
public class WeeklyPlannerApp {

    // 1. Define your domain enum
    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    // 2. Use EnumSet to group days
    private static final EnumSet<Day> WEEKEND = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
    private static final EnumSet<Day> WORKDAYS = EnumSet.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY);

    // 3. Use EnumMap to associate each day with an activity
    private final EnumMap<Day, String> activityMap = new EnumMap<>(Day.class);

    public WeeklyPlannerApp() {
        // populate default activities
        activityMap.put(Day.MONDAY, "Team stand-up @ 9am");
        activityMap.put(Day.TUESDAY, "Client review meeting");
        activityMap.put(Day.WEDNESDAY, "Code refactoring session");
        activityMap.put(Day.THURSDAY, "Tech talk at 4pm");
        activityMap.put(Day.FRIDAY, "Deploy new release");
        activityMap.put(Day.SATURDAY, "Hiking with friends");
        activityMap.put(Day.SUNDAY, "Family brunch");
    }

    // 4. Print the plan for a given day
    public void printPlanFor(Day day) {
        if (WORKDAYS.contains(day)) {
            log.info("{} is work day", activityMap.get(day));
        } else {
            log.info("{} is Weekend", activityMap.get(day));
        }


        // b) Lookup the activity
        log.info("  -> Activity: " + activityMap.get(day));
        switch (day) {
            case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> log.info("  -> Weekly Planner");
            case SATURDAY -> log.info("  -> Saturday");
            case SUNDAY -> log.info("  -> Sunday");
        }
    }

    public static void main(String[] args) {
        WeeklyPlannerApp planner = new WeeklyPlannerApp();
        // Print the plan for every day of the week
        for (Day day : Day.values()) {
            planner.printPlanFor(day);
        }
    }
}
