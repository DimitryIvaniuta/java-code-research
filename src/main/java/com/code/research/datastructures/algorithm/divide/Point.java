package com.code.research.datastructures.algorithm.divide;

/**
 * Represents a point in 2D space.
 */
public class Point {

    /** The x-coordinate of the point. */
    final double x;

    /** The y-coordinate of the point. */
    final double y;

    /**
     * Constructs a point with the specified coordinates.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

}
