package com.code.research.datastructures.algorithm.greedy;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ActivitySelectionApplication {

    /**
     * Main method demonstrating the Activity Selection algorithm.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity("A", 1, 4));
        activities.add(new Activity("B", 3, 5));
        activities.add(new Activity("C", 0, 6));
        activities.add(new Activity("D", 5, 7));
        activities.add(new Activity("E", 3, 9));
        activities.add(new Activity("F", 5, 9));
        activities.add(new Activity("G", 6, 10));
        activities.add(new Activity("H", 8, 11));
        activities.add(new Activity("I", 8, 12));
        activities.add(new Activity("J", 2, 14));
        activities.add(new Activity("K", 12, 16));

        List<Activity> selectedActivities = ActivitySelection.selectActivities(activities);
        log.info("Selected Activities:");
        selectedActivities.forEach(e -> log.info(e.toString()));
    }

}
