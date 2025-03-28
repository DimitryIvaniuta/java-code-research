package com.code.research.datastructures.algorithm.greedy;

/**
 * Represents an activity with a name, start time, and finish time.
 *
 * <p>In scheduling problems, each activity requires exclusive usage of a resource.
 * The goal is to select the maximum number of non-overlapping activities.
 */
public class Activity {

    /** The name of the activity. */
    private final String name;

    /** The start time of the activity (inclusive). */
    private final int start;

    /** The finish time of the activity (exclusive). */
    private final int finish;

    /**
     * Constructs an Activity with the specified name, start time, and finish time.
     *
     * @param name   the name of the activity
     * @param start  the start time (inclusive)
     * @param finish the finish time (exclusive)
     * @throws IllegalArgumentException if finish is not greater than start or if times are negative
     */
    public Activity(String name, int start, int finish) {
        if (start < 0 || finish < 0 || finish <= start) {
            throw new IllegalArgumentException("Invalid start and finish times.");
        }
        this.name = name;
        this.start = start;
        this.finish = finish;
    }

    /**
     * Returns the name of the activity.
     *
     * @return the activity name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the start time of the activity.
     *
     * @return the start time
     */
    public int getStart() {
        return start;
    }

    /**
     * Returns the finish time of the activity.
     *
     * @return the finish time
     */
    public int getFinish() {
        return finish;
    }

    @Override
    public String toString() {
        return String.format("Activity{name='%s', start=%d, finish=%d}", name, start, finish);
    }

}
