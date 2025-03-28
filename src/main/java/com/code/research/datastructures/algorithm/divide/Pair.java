package com.code.research.datastructures.algorithm.divide;

/**
 * Represents a pair of points along with their Euclidean distance.
 */
public class Pair {

    /** The first point in the pair. */
    final Point p1;

    /** The second point in the pair. */
    final Point p2;

    /** The Euclidean distance between p1 and p2. */
    final double distance;

    /**
     * Constructs a Pair with the specified points and pre-computed distance.
     *
     * @param p1       the first point.
     * @param p2       the second point.
     * @param distance the Euclidean distance between p1 and p2.
     */
    public Pair(Point p1, Point p2, double distance) {
        this.p1 = p1;
        this.p2 = p2;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return String.format("Pair: %s and %s, Distance: %.4f", p1, p2, distance);
    }

}