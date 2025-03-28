package com.code.research.datastructures.algorithm.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ActivitySelection provides a greedy algorithm to select the maximum number of non-overlapping activities.
 *
 * <p>The greedy strategy is:
 * <ol>
 *   <li>Sort the activities by their finish times in ascending order.</li>
 *   <li>Select the first activity, then iterate through the remaining activities,
 *   choosing an activity only if its start time is greater than or equal to the finish time of the last selected activity.</li>
 * </ol>
 *
 * <p>This approach is widely used in resource scheduling and event planning.
 */
public class ActivitySelection {

    private ActivitySelection() {
        //
    }

    /**
     * Selects the maximum number of non-overlapping activities from the provided list.
     *
     * @param activities the list of activities to choose from
     * @return a list of selected activities that do not overlap
     */
    public static List<Activity> selectActivities(List<Activity> activities) {
        if (activities == null || activities.isEmpty()) {
            return Collections.emptyList();
        }
        // Sort activities by their finish time in ascending order.
        activities.sort(Comparator.comparingInt(Activity::getFinish));

        List<Activity> selected = new ArrayList<>();
        // Always select the first activity after sorting.
        Activity lastSelected = activities.getFirst();
        selected.add(lastSelected);

        // Iterate through the remaining activities.
        for (int i = 1; i < activities.size(); i++) {
            Activity current = activities.get(i);
            if (current.getStart() >= lastSelected.getFinish()) {
                selected.add(current);
                lastSelected = current;
            }
        }
        return selected;
    }

}
